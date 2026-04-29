package com.hariprasanna.shogi.dto;

import com.hariprasanna.shogi.engine.PlayerColor;

public record MoveRequestDTO(
         PlayerColor requestingPlayer, // "BLACK" or "WHITE"
         int startRow,
         int startColumn,
         int endRow,
         int endColumn,
         boolean promote
) {}