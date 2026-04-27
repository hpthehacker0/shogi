package com.hariprasanna.shogi.engine;

public class ShogiBoard {
    private final GamePiece[][] grid;

    public ShogiBoard(){
        this.grid = new GamePiece[9][9];
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
}
