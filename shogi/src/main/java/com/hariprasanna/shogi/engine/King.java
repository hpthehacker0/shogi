package com.hariprasanna.shogi.engine;

import java.util.List;

public class King extends AbstractPiece {

    public King(PlayerColor player, String name) {
        super(player, name);
    }

    @Override
    public List<Position> getLegalMoves(Position currentPosition, ShogiBoard board) {

        return getKingLegalMoves(currentPosition,board);
    }

    @Override
    public PromotionStatus checkPromotion(Position targetPosition) {
        return PromotionStatus.NONE; // Kings never promote!
    }
    @Override
    public GamePiece cloneForCaptor(PlayerColor newOwner) {
        return new King(newOwner, "King");
    }
}