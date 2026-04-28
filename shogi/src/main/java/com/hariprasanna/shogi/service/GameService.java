package com.hariprasanna.shogi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hariprasanna.shogi.dto.PieceDTO;
import com.hariprasanna.shogi.engine.GamePiece;
import com.hariprasanna.shogi.engine.Position;
import com.hariprasanna.shogi.engine.ShogiBoard;
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

        // TODO 1: Loop through the 9x9 board grid (rows 0-8, cols 0-8)
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


        // TODO 2: Loop through the Black Hand (board.getBlackHand())
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

        // TODO 3: Loop through the White Hand (board.getWhiteHand())
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
}