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
}
