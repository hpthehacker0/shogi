package com.hariprasanna.shogi.engine;

public record Position(int row ,int column) {


    public boolean isWithinBounds(){
        if(row >=0 && row<=8 && column >=0 && column <=8){
            return true;
        }
        return false;
    }

}
