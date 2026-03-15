package com.sisuz.pos.domain.terminal.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PosTerminalCreateRequest(

        @NotNull
        UUID storeId,

        @NotNull
        @Size(max = 30)
        String code,

        @NotNull
        @Size(max = 80)
        String name
) {
}
