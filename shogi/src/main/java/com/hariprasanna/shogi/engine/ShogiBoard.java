package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.List;

public class ShogiBoard {
    private final GamePiece[][] grid;
    // The Komadai (Hands)
    private final List<GamePiece> blackHand;
    private final List<GamePiece> whiteHand;

    public ShogiBoard(){
        this.grid = new GamePiece[9][9];
        this.blackHand = new ArrayList<>();
        this.whiteHand = new ArrayList<>();
        setupInitialPosition();
    }

    public GamePiece getPiece(Position position){
        if(!position.isWithinBounds()){
            return  null;
        }
        return  grid[position.row()][position.column()];
    }
    public void setPiece(Position position,GamePiece piece){
        if(position.isWithinBounds()){
            grid[position.row()][position.column()] = piece;
        }
    }
    public void movePiece(Position start,Position end){
        GamePiece pieceToMove = getPiece(start);
        GamePiece targetPiece = getPiece(end);
        if(targetPiece != null) {

            PlayerColor captorColor = pieceToMove.getPlayer();
            GamePiece clonedPiece = targetPiece.cloneForCaptor(captorColor);

            if (captorColor == PlayerColor.BLACK) {
                blackHand.add(clonedPiece);
            }
            else {
                whiteHand.add(clonedPiece);
            }
        }

        setPiece(end,pieceToMove);
        setPiece(start,null);
    }
    /**
     * Instantiates all 40 pieces and places them in their starting positions.
     */
    private void setupInitialPosition() {

        // ♟️ 1. The Pawns (Using your efficient loop!)
        for (int i = 0; i < 9; i++) {
            setPiece(new Position(6, i), new Pawn(PlayerColor.BLACK, "Pawn"));
            setPiece(new Position(2, i), new Pawn(PlayerColor.WHITE, "Pawn"));
        }

        // 🏯 2. The Major Pieces (Rook & Bishop)
        // Black (Row 7)
        setPiece(new Position(7, 7), new Rook(PlayerColor.BLACK, "Rook"));
        setPiece(new Position(7, 1), new Bishop(PlayerColor.BLACK, "Bishop"));
        // White (Row 1)
        setPiece(new Position(1, 1), new Rook(PlayerColor.WHITE, "Rook"));
        setPiece(new Position(1, 7), new Bishop(PlayerColor.WHITE, "Bishop"));

        // 👑 3. The Back Rank
        // (Note: Ensure your SilverGeneral class is spelled correctly without the typo!)

        // Black Command Line (Row 8)
        setPiece(new Position(8, 0), new Lance(PlayerColor.BLACK, "Lance"));
        setPiece(new Position(8, 8), new Lance(PlayerColor.BLACK, "Lance"));
        setPiece(new Position(8, 1), new Knight(PlayerColor.BLACK, "Knight"));
        setPiece(new Position(8, 7), new Knight(PlayerColor.BLACK, "Knight"));
        setPiece(new Position(8, 2), new SilverGeneral(PlayerColor.BLACK, "Silver General"));
        setPiece(new Position(8, 6), new SilverGeneral(PlayerColor.BLACK, "Silver General"));
        setPiece(new Position(8, 3), new GoldGeneral(PlayerColor.BLACK, "Gold General"));
        setPiece(new Position(8, 5), new GoldGeneral(PlayerColor.BLACK, "Gold General"));
        setPiece(new Position(8, 4), new King(PlayerColor.BLACK, "King"));

        // White Command Line (Row 0)
        setPiece(new Position(0, 0), new Lance(PlayerColor.WHITE, "Lance"));
        setPiece(new Position(0, 8), new Lance(PlayerColor.WHITE, "Lance"));
        setPiece(new Position(0, 1), new Knight(PlayerColor.WHITE, "Knight"));
        setPiece(new Position(0, 7), new Knight(PlayerColor.WHITE, "Knight"));
        setPiece(new Position(0, 2), new SilverGeneral(PlayerColor.WHITE, "Silver General"));
        setPiece(new Position(0, 6), new SilverGeneral(PlayerColor.WHITE, "Silver General"));
        setPiece(new Position(0, 3), new GoldGeneral(PlayerColor.WHITE, "Gold General"));
        setPiece(new Position(0, 5), new GoldGeneral(PlayerColor.WHITE, "Gold General"));
        setPiece(new Position(0, 4), new King(PlayerColor.WHITE, "King"));
    }

    private boolean hasUnpromotedPawnOnColumn(PlayerColor player, int column) {
        for (int row = 0; row < 9; row++) {
            GamePiece piece = getPiece(new Position(row, column));

            // We must check 4 things: Does it exist? Is it yours? Is it a Pawn? Is it unpromoted?
            if (piece != null
                    && piece.getPlayer() == player
                    && piece.getName().equals("Pawn")
                    && !piece.isPromoted()) {
                return true;
            }
        }
        return false;
    }

    private boolean violatesZeroMoveRule(PlayerColor player, GamePiece piece, Position target) {
        String name = piece.getName();
        int row = target.row();

        if (player == PlayerColor.BLACK) {
            if ((name.equals("Pawn") || name.equals("Lance")) && row == 0) return true;
            if (name.equals("Knight") && (row == 0 || row == 1)) return true;
        } else { // WHITE
            if ((name.equals("Pawn") || name.equals("Lance")) && row == 8) return true;
            if (name.equals("Knight") && (row == 8 || row == 7)) return true;
        }

        return false;
    }
    public boolean dropPiece(PlayerColor player, GamePiece pieceToDrop, Position targetPosition) {

        // --- VALIDATION PHASE ---

        // 1. Is the target square empty?
        // TODO: Write an if statement using getPiece(). If not empty, return false.
        if(getPiece(targetPosition) != null){
            return false;
        }

        // 2. Do you actually have this piece in your hand?
        List<GamePiece> activeHand = (player == PlayerColor.BLACK) ? blackHand : whiteHand;
        if (!activeHand.contains(pieceToDrop)) {
            return false;
        }

        // 3. Does it violate the Zero-Move rule?
        // TODO: Use our new violatesZeroMoveRule helper. If true, return false.
        if(violatesZeroMoveRule(player,pieceToDrop,targetPosition)){
            return false;
        }

        // 4. Does it violate Nifu (Double Pawn)?
        // TODO: If the pieceToDrop is a "Pawn", use our new hasUnpromotedPawnOnColumn helper.
        // If that helper returns true, return false.
        if(pieceToDrop.getName().equals("Pawn")){
            if(hasUnpromotedPawnOnColumn(player, targetPosition.column())){
                return false;
            }
        }


        // --- EXECUTION PHASE ---

        // If we survived all those checks, the drop is legal!
        // TODO: Remove the piece from the activeHand.
        // TODO: Place the piece on the board using setPiece().

        setPiece(targetPosition,pieceToDrop);
        activeHand.remove(pieceToDrop);


        return true;
    }
}
