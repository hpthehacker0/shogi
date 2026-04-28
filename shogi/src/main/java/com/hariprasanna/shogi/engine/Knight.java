package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.List;

import static com.hariprasanna.shogi.engine.PlayerColor.BLACK;
import static com.hariprasanna.shogi.engine.PlayerColor.WHITE;
import static com.hariprasanna.shogi.engine.PromotionStatus.MANDATORY;

public class Knight extends AbstractPiece{
    public Knight(PlayerColor player, String name) {
        super(player, name);
    }
    private static final int[][] BLACK_DIRECTIONS= {{-2,-1},{-2,1}};
    private static final int[][] WHITE_DIRECTIONS= {{2,-1},{2,1}};

    @Override
    public List<Position> getLegalMoves(Position currentPosition, ShogiBoard board) {
        if(this.isPromoted){
            return getGoldLegalMoves(currentPosition,board);
        }
        List<Position> legalMoves = new ArrayList<>();

        int[][] activeDirection = (player == BLACK)?BLACK_DIRECTIONS:WHITE_DIRECTIONS;

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

    @Override
    public PromotionStatus checkPromotion(Position targetPosition) {
        if((targetPosition.row() ==0 || targetPosition.row() ==1) && player == BLACK){
            return MANDATORY;
        }
        if((targetPosition.row() ==8 || targetPosition.row() ==7) && player == WHITE){
            return MANDATORY;
        }

        return super.checkPromotion(targetPosition);
    }
    @Override
    public GamePiece cloneForCaptor(PlayerColor newOwner) {
        return new Pawn(newOwner, "Pawn");
    }
}

