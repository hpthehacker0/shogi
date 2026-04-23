package com.hariprasanna.shogi;

import com.hariprasanna.shogi.engine.Pawn;
import com.hariprasanna.shogi.engine.PlayerColor;
import com.hariprasanna.shogi.engine.Position;
import com.hariprasanna.shogi.engine.ShogiBoard;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("♟️ Initializing Shogi Engine Test...\n");

        // 1. Create the board
        ShogiBoard board = new ShogiBoard();

        // 2. Create our pieces
        Pawn blackPawn = new Pawn(PlayerColor.BLACK, "Pawn");
        Pawn whitePawn = new Pawn(PlayerColor.WHITE, "Pawn");

        // 3. Define their starting positions
        Position blackStart = new Position(6, 4);
        Position whiteStart = new Position(2, 4);

        // 4. Place them on the board
        board.setPiece(blackStart, blackPawn);
        board.setPiece(whiteStart, whitePawn);

        // 5. Calculate and print Black Pawn's moves
        // We expect Black to move "UP" the board (Row decreases to 5)
        List<Position> blackMoves = blackPawn.getLegalMoves(blackStart, board);
        System.out.println("Black Pawn at " + blackStart + " can move to: " + blackMoves);

        // 6. Calculate and print White Pawn's moves
        // We expect White to move "DOWN" the board (Row increases to 3)
        List<Position> whiteMoves = whitePawn.getLegalMoves(whiteStart, board);
        System.out.println("White Pawn at " + whiteStart + " can move to: " + whiteMoves);

        // 7. Optional Challenge: What happens if Black reaches the end?
        Position endOfBoard = new Position(1, 4);
        System.out.println("\nIf Black Pawn reaches " + endOfBoard + "...");
        System.out.println("Promotion Status: " + blackPawn.checkPromotion(endOfBoard));
    }
}