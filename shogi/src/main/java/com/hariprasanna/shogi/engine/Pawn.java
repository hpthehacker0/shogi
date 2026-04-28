package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.List;

import static com.hariprasanna.shogi.engine.PlayerColor.BLACK;
import static com.hariprasanna.shogi.engine.PlayerColor.WHITE;
import static com.hariprasanna.shogi.engine.PromotionStatus.MANDATORY;

public class Pawn extends AbstractPiece{
    public Pawn(PlayerColor player, String name) {
        super(player, name);
    }

    @Override
    public List<Position> getLegalMoves(Position currentPosition, ShogiBoard board) {
        if(this.isPromoted){
            return getGoldLegalMoves(currentPosition,board);
        }
        List<Position> legalMoves = new ArrayList<>();

        int direction = (player == BLACK)?-1:1;
            int targetrow = currentPosition.row() + direction;
            int targetcolumn = currentPosition.column();
            Position target = new Position(targetrow, targetcolumn);
            if (isValidDestination(target, board)) {
                legalMoves.add(target);


            }
        return legalMoves;
    }

    @Override
    public PromotionStatus checkPromotion(Position targetPosition) {
        if(targetPosition.row() ==0 && player == BLACK){
            return MANDATORY;
        }
        if(targetPosition.row() ==8 && player == WHITE){
            return MANDATORY;
        }

        return super.checkPromotion(targetPosition);
    }
    @Override
    public GamePiece cloneForCaptor(PlayerColor newOwner) {
        return new Pawn(newOwner, "Pawn");
    }


}
