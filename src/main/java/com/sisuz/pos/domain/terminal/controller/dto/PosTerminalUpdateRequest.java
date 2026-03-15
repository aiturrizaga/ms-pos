package com.sisuz.pos.domain.terminal.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PosTerminalUpdateRequest(
        @NotNull
        @Size(max = 80)
        String name
) {
}
