package com.hariprasanna.shogi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hariprasanna.shogi.engine.PlayerColor;

public record MoveRequestDTO(
        @JsonProperty("requestingPlayer") PlayerColor requestingPlayer,
        @JsonProperty("startRow") int startRow,

        // CRITICAL: Make sure these map 'startCol' to 'startColumn'
        @JsonProperty("startCol") int startColumn,

        @JsonProperty("endRow") int endRow,

        // CRITICAL: Make sure this maps 'endCol' to 'endColumn'
        @JsonProperty("endCol") int endColumn,

        @JsonProperty("promote") boolean promote,

        // The Drop Variables
        @JsonProperty("isDrop") boolean isDrop,
        @JsonProperty("dropPieceName") String dropPieceName
) {}