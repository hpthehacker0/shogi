package com.hariprasanna.shogi.engine;

import java.util.List;

public interface GamePiece {
    // checks for black or white player
    PlayerColor getPlayer();
    // returns all legal moves of the game piece from the given position
    List<Position> getLegalMoves(Position currentPosition, ShogiBoard board);
    //returns name of the piece
    String getName();
    //check for the game piece is promoted or not

    PromotionStatus checkPromotion(Position targetPosition);

    void promote();

}
