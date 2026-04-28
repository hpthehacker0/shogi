package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.List;

import static com.hariprasanna.shogi.engine.PlayerColor.BLACK;
import static com.hariprasanna.shogi.engine.PlayerColor.WHITE;
import static com.hariprasanna.shogi.engine.PromotionStatus.NONE;
import static com.hariprasanna.shogi.engine.PromotionStatus.OPTIONAL;

public  abstract class AbstractPiece implements GamePiece {
    protected final PlayerColor player;
    protected boolean isPromoted;
    protected final String name;


    public AbstractPiece(PlayerColor player,String name){
        this.player = player;
        this.name = name;
        this.isPromoted = false;
    }

    @Override
    public PlayerColor getPlayer() {
        return this.player;
    }

    @Override
    public String getName() {
        return isPromoted ? "Promoted " + this.name : this.name;
    }

    protected boolean isValidDestination(Position target,ShogiBoard board){
        if(!target.isWithinBounds()){
            return false;
        }
        GamePiece pieceAtTarget = board.getPiece(target);
        if(pieceAtTarget == null){
            return  true;
        }
        return pieceAtTarget.getPlayer() !=this.getPlayer();
    }

    @Override
    public abstract List<Position> getLegalMoves(Position currentPosition, ShogiBoard board);

    @Override
    public PromotionStatus checkPromotion(Position targetPosition) {

//        int [] enemyCamp = (player ==BLACK)?new int[]{0,1,2}:new int[]{6,7,8};
//
//        if(targetPosition.row() == enemyCamp[0] ||targetPosition.row() == enemyCamp[1] ||targetPosition.row() == enemyCamp[2]){
//            return OPTIONAL;
//        }
        if(player == BLACK && targetPosition.row() <=2){
            return OPTIONAL;

        }
        if(player == WHITE && targetPosition.row() >=6){
            return OPTIONAL;

        }


        return NONE;
    }
    protected List<Position> getSlidingMovesInDirection(Position start, int rowOffset, int colOffset, ShogiBoard board) {
        List<Position> slidingMoves = new ArrayList<>();

        int currentRow = start.row();
        int currentColumn = start.column();

        while (true) {
            currentRow += rowOffset;
            currentColumn += colOffset;
            Position target = new Position(currentRow, currentColumn);

            if (!target.isWithinBounds()) {
                break;
            }

            GamePiece pieceAtTarget = board.getPiece(target);

            if (pieceAtTarget == null) {
                slidingMoves.add(target);
            } else {

                if (pieceAtTarget.getPlayer() != this.player) {
                    slidingMoves.add(target); // Capture!
                }
                break;
            }
        }

        return slidingMoves;
    }

    @Override
    public void promote() {
        this.isPromoted = true;
    }

    private static final  int[][] GOLD_BLACK_DIRECTIONS = {
            {-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1}
    };
    private static final  int[][] GOLD_WHITE_DIRECTIONS = {
            {-1,0},{1,0},{0,-1},{0,1},{1,-1},{1,1}
    };


    public List<Position> getGoldLegalMoves(Position currentPosition, ShogiBoard board) {
        List<Position> legalMoves = new ArrayList<>();

        int[][] activeDirection = (player == PlayerColor.BLACK)?GOLD_BLACK_DIRECTIONS:GOLD_WHITE_DIRECTIONS;

        for(int[] directions: activeDirection){
            int targetrow = currentPosition.row() + directions[0];
            int targetcolumn = currentPosition.column() + directions[1];
            Position target = new Position(targetrow,targetcolumn);
            if(isValidDestination(target,board)){
                legalMoves.add(target);
            }
        }


        return legalMoves;
    }

    // 1. Define the 8 possible offsets (rowOffset, colOffset)
    private static final int[][] KING_DIRECTIONS = {
            {-1, 0},  // Up
            {1, 0},   // Down
            {0, -1},  // Left
            {0, 1},   // Right
            {-1, -1}, // Up-Left
            {-1, 1},  // Up-Right
            {1, -1},  // Down-Left
            {1, 1}    // Down-Right
    };




    public List<Position> getKingLegalMoves(Position current, ShogiBoard board) {
        List<Position> legalMoves = new ArrayList<>();

        // 2. Loop through every offset in our array
        for (int[] direction : KING_DIRECTIONS) {

            // Calculate the potential target
            int targetRow = current.row() + direction[0];
            int targetCol = current.column() + direction[1];
            Position target = new Position(targetRow, targetCol);

            // 3. Let our AbstractPiece do the heavy lifting!
            if (isValidDestination(target, board)) {
                legalMoves.add(target);
            }
        }
        return legalMoves;
    }

}
