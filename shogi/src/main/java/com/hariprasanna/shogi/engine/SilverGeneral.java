package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.List;

public class SilverGeneral extends AbstractPiece{
    public SilverGeneral(PlayerColor player, String name) {
        super(player, name);
    }
   private static final int[][] BLACK_DIRECTIONS= {{-1,-1},{-1,0},{-1,1},{1,-1},{1,1}};
    private static final int[][] WHITE_DIRECTIONS= {{1,-1},{1,0},{1,1},{-1,-1},{-1,1}};



    @Override
    public List<Position> getLegalMoves(Position currentPosition, ShogiBoard board) {
        if(this.isPromoted){
            return getGoldLegalMoves(currentPosition,board);
        }
        List<Position> legalMoves = new ArrayList<>();

        int[][] activeDirection = (player == PlayerColor.BLACK)?BLACK_DIRECTIONS:WHITE_DIRECTIONS;

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
    public GamePiece cloneForCaptor(PlayerColor newOwner) {
        return new Pawn(newOwner, "Pawn");
    }


}
