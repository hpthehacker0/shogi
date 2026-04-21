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
        List<Position> legalmoves = new ArrayList<>();

        int direction = (player == BLACK)?-1:1;
            int targetrow = currentPosition.row() + direction;
            int targetcolumn = currentPosition.column();
            Position target = new Position(targetrow, targetcolumn);
            if (isValidDestination(target, board)) {
                legalmoves.add(target);


            }
        return legalmoves;
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


}
