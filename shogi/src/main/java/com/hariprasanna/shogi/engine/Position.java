package com.hariprasanna.shogi.engine;

public record Position(int row ,int column) {


    public boolean isWithinBounds(){
        return row >= 0 && row <= 8 && column >= 0 && column <= 8;
    }

}
