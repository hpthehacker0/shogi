package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Rook extends AbstractPiece{
    public Rook(PlayerColor player, String name) {
        super(player, name);
    }
    private static final  int[][] DIRECTIONS = {
            {-1,0},{1,0},{0,-1},{0,1}
    };

    @Override
    public List<Position> getLegalMoves(Position currentPosition, ShogiBoard board) {
        HashSet<Position> legalMoves = new HashSet<>();

        for(int [] direction : DIRECTIONS){

            legalMoves.addAll(getSlidingMovesInDirection(currentPosition, direction[0], direction[1], board));
        }
        if (this.isPromoted) {
            legalMoves.addAll(getKingLegalMoves(currentPosition, board));
        }
        return new ArrayList<>(legalMoves);
    }
    @Override
    public GamePiece cloneForCaptor(PlayerColor newOwner) {

        return new Rook(newOwner, "Rook");
    }
}
