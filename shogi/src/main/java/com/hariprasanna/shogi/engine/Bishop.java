package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends  AbstractPiece{
    public Bishop(PlayerColor player, String name) {
        super(player, name);
    }
    private static final int[][] DIRECTIONS= {{-1,-1},{-1,1},{1,-1},{1,1}};


    @Override
    public List<Position> getLegalMoves(Position currentPosition, ShogiBoard board) {
        List<Position> legalMoves = new ArrayList<>();

        for(int [] direction : DIRECTIONS){

            legalMoves.addAll(getSlidingMovesInDirection(currentPosition, direction[0], direction[1], board));
        }
        return legalMoves;
    }
}
