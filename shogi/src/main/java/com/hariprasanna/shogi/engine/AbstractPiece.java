package com.hariprasanna.shogi.engine;

import java.util.List;

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
        return isPromoted ? "Promoted" + this.name : this.name;
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
}
