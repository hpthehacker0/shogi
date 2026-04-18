package com.hariprasanna.shogi.engine;

public class ShogiBoard {
    private final GamePiece[][] grid;

    public ShogiBoard(){
        this.grid = new GamePiece[9][9];
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
}
