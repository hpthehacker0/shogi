package com.hariprasanna.shogi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hariprasanna.shogi.dto.MoveRequestDTO;
import com.hariprasanna.shogi.dto.PieceDTO;
import com.hariprasanna.shogi.engine.*;
import com.hariprasanna.shogi.model.Game;
import com.hariprasanna.shogi.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final ObjectMapper objectMapper;

    // Spring Boot automatically injects these dependencies for us!
    public GameService(GameRepository gameRepository, ObjectMapper objectMapper) {
        this.gameRepository = gameRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Converts a living ShogiBoard object into a flat JSON String.
     */
    public String serializeBoard(ShogiBoard board) throws JsonProcessingException {
        List<PieceDTO> allPieces = new ArrayList<>();

        // If a piece is there, create a new PieceDTO and add it to the list.
        // Set the location string to "BOARD".
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                GamePiece piece = board.getPiece(new Position(row, col));

                if (piece != null) {
                    PieceDTO dto = new PieceDTO(
                            piece.getPlayer(),
                            piece.getName(),
                            piece.isPromoted(),
                            row,
                            col,
                            "BOARD"
                    );
                    allPieces.add(dto);
                }
            }
        }



        // Create PieceDTOs for them. Since they aren't on the board,
        // you can set row and col to -1, and location to "BLACK_HAND".
        for (GamePiece piece : board.getBlackHand()) {
            PieceDTO dto = new PieceDTO(
                    piece.getPlayer(),
                    piece.getName(),
                    piece.isPromoted(),
                    -1,
                    -1,
                    "BLACK_HAND"
            );
            allPieces.add(dto);
        }


        // Create PieceDTOs for them with row/col -1 and location "WHITE_HAND".
        for (GamePiece piece : board.getWhiteHand()) {
            PieceDTO dto = new PieceDTO(
                    piece.getPlayer(),
                    piece.getName(),
                    piece.isPromoted(),
                    -1,
                    -1,
                    "WHITE_HAND"
            );
            allPieces.add(dto);
        }

        // Finally, tell Jackson to turn our simple list of DTOs into a JSON String!
        return objectMapper.writeValueAsString(allPieces);
    }

    public ShogiBoard deserializeBoard(String json) throws JsonProcessingException {
        // 1. Tell Jackson to turn the JSON string back into a List of PieceDTOs
        List<PieceDTO> allPieces = objectMapper.readValue(json, new TypeReference<List<PieceDTO>>() {});

        // 2. Create a BLANK board using our new constructor
        ShogiBoard board = new ShogiBoard(true);

        // 3. Loop through every DTO we loaded
        for (PieceDTO dto : allPieces) {

            // Turn the dumb DTO into a living piece
            GamePiece livingPiece = createPieceFromDTO(dto);

            // TODO: Place the livingPiece in the correct spot!
            // Look at dto.location().
            if(dto.location().equals("BOARD")){
                board.setPiece(new Position(dto.row(), dto.col()), livingPiece);
            }
            if(dto.location().equals("BLACK_HAND")){
                board.getBlackHand().add(livingPiece);

            }
            if(dto.location().equals("WHITE_HAND")){
                board.getWhiteHand().add(livingPiece);

            }
        }

        return board;
    }
    private GamePiece createPieceFromDTO(PieceDTO dto) {

        // 🧹 Clean the string: Remove "Promoted " if it's there!
        String baseName = dto.name().replace("Promoted ", "");

        // Now we use our clean baseName for the switch!
        GamePiece piece = switch (baseName) {
            case "Pawn" -> new Pawn(dto.player(), baseName);
            case "Lance" -> new Lance(dto.player(), baseName);
            case "Knight" -> new Knight(dto.player(), baseName);
            case "Silver General" -> new SilverGeneral(dto.player(), baseName);
            case "Gold General" -> new GoldGeneral(dto.player(), baseName);
            case "Bishop" -> new Bishop(dto.player(), baseName);
            case "Rook" -> new Rook(dto.player(), baseName);
            case "King" -> new King(dto.player(), baseName);
            default -> throw new IllegalArgumentException("Unknown piece: " + baseName);
        };

        // Was it promoted? If so, manually trigger the promotion!
        if (dto.isPromoted()) {
            piece.promote();
        }

        return piece;
    }


    public Game processMove(Long gameId, MoveRequestDTO moveRequest) throws Exception {

        // 1. Fetch the Game State from the database
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        // 2. STATE CHECK: Is the game actually active?
        if (!"IN_PROGRESS".equals(game.getStatus())) {
            throw new IllegalStateException("Game is not in progress.");
        }

        // 3. STATE CHECK: Is it actually this player's turn?
        if (game.getCurrentTurn() != moveRequest.requestingPlayer()) {
            throw new IllegalStateException("It is not your turn!");
        }

        // 4. Bring the board to life
        ShogiBoard board = deserializeBoard(game.getBoardStateJson());

// 5. Create our target destination
        Position end = new Position(moveRequest.endRow(), moveRequest.endColumn());

// --- 🌟 NEW: THE DROP ROUTER ---
        if (moveRequest.isDrop()) {

            // 1. Find the right hand
            List<GamePiece> hand = (moveRequest.requestingPlayer() == PlayerColor.BLACK) ? board.getBlackHand() : board.getWhiteHand();

            // 2. Find the piece they want to drop
            GamePiece pieceToDrop = hand.stream()
                    .filter(p -> p.getName().equals(moveRequest.dropPieceName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Cheating Detected: You don't have that piece in your hand."));

            // 3. 🌟 USE THE ENGINE'S LOGIC! 🌟
            // The board.dropPiece() method automatically checks for Nifu, Zero-Move, and occupied squares.
            boolean successfulDrop = board.dropPiece(moveRequest.requestingPlayer(), pieceToDrop, end);

            if (!successfulDrop) {
                // If the engine returns false, the drop was illegal!
                throw new IllegalArgumentException("Illegal Drop: Violated Nifu (Double Pawn), Zero-Move rule, or square is occupied.");
            }

        } else {

            Position start = new Position(moveRequest.startRow(), moveRequest.startColumn());
            GamePiece pieceToMove = board.getPiece(start);

            if (pieceToMove == null || pieceToMove.getPlayer() != moveRequest.requestingPlayer()) {
                throw new IllegalArgumentException("Invalid piece selection.");
            }

            PromotionStatus promoStatus = pieceToMove.checkPromotion(end);

            List<Position> legalMoves = board.getSafeLegalMoves(start);
            if (!legalMoves.contains(end)) {
                throw new IllegalArgumentException("Illegal move: That piece cannot move there.");
            }

            board.movePiece(start, end);

            // Execute Promotion
            GamePiece movedPiece = board.getPiece(end);
            if (promoStatus == PromotionStatus.MANDATORY) {
                movedPiece.promote();
            } else if (promoStatus == PromotionStatus.OPTIONAL && moveRequest.promote()) {
                movedPiece.promote();
            } else if (promoStatus == PromotionStatus.NONE && moveRequest.promote()) {
                throw new IllegalArgumentException("Cheating detected: Cannot promote here.");
            }
        }
        // -------------------------------

        // 8. UPDATE STATE: Flip the turn!
        PlayerColor nextTurn = (game.getCurrentTurn() == PlayerColor.BLACK)
                ? PlayerColor.WHITE
                : PlayerColor.BLACK;
        game.setCurrentTurn(nextTurn);
        // 9. THE CHECKMATE DETECTOR
        if (board.isInCheck(nextTurn)) {
            // They are in check. Do they have any way out?
            if (!board.hasAnySafeMoves(nextTurn)) {
                System.out.println("🏆 CHECKMATE! Game Over.");
                game.setStatus("CHECKMATE");
            } else {
                System.out.println("🚨 CHECK! " + nextTurn + " is under attack!");
                // Optional: You could set game.setStatus("CHECK") here if you want!
            }
        }

        // 10. Save the new state back to the database
        game.setBoardStateJson(serializeBoard(board));
        return gameRepository.save(game);
    }

}
