package com.hariprasanna.shogi.dto;

import com.hariprasanna.shogi.engine.PlayerColor;

// This object has zero logic. It just holds data for saving!
public record PieceDTO(
        PlayerColor player,
        String name,
        boolean isPromoted,
        int row,
        int col,
        String location // We will use "BOARD", "BLACK_HAND", or "WHITE_HAND"
) {}