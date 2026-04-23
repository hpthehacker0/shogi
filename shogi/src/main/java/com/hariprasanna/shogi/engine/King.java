package com.hariprasanna.shogi.engine;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractPiece {

    // 1. Define the 8 possible offsets (rowOffset, colOffset)
    private static final int[][] KING_DIRECTIONS = {
            {-1, 0},  // Up
            {1, 0},   // Down
            {0, -1},  // Left
            {0, 1},   // Right
            {-1, -1}, // Up-Left
            {-1, 1},  // Up-Right
            {1, -1},  // Down-Left
            {1, 1}    // Down-Right
    };

    public King(PlayerColor player, String name) {
        super(player, name);
    }

    @Override
    public List<Position> getLegalMoves(Position current, ShogiBoard board) {
        List<Position> legalMoves = new ArrayList<>();

        // 2. Loop through every offset in our array
        for (int[] offset : KING_DIRECTIONS) {

            // Calculate the potential target
            int targetRow = current.row() + offset[0];
            int targetCol = current.column() + offset[1];
            Position target = new Position(targetRow, targetCol);

            // 3. Let our AbstractPiece do the heavy lifting!
            if (isValidDestination(target, board)) {
                legalMoves.add(target);
            }
        }
        return legalMoves;
    }

    @Override
    public PromotionStatus checkPromotion(Position targetPosition) {
        return PromotionStatus.NONE; // Kings never promote!
    }
}