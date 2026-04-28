package com.hariprasanna.shogi.engine;
import java.util.List;

public class GoldGeneral extends AbstractPiece{

    public GoldGeneral(PlayerColor player, String name) {
        super(player, name);
    }


    @Override
    public List<Position> getLegalMoves(Position currentPosition, ShogiBoard board) {


    return getGoldLegalMoves(currentPosition,board);
    }


    @Override
    public PromotionStatus checkPromotion(Position targetPosition) {
        return PromotionStatus.NONE;
    }
}
