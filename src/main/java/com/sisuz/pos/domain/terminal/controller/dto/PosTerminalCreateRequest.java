package com.sisuz.pos.domain.terminal.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PosTerminalCreateRequest(

        @NotNull
        Long storeId,

        @NotNull
        @Size(max = 30)
        String code,

        @NotNull
        @Size(max = 80)
        String name
) {
}
