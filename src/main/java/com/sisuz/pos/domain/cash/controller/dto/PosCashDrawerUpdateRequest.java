package com.sisuz.pos.domain.cash.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PosCashDrawerUpdateRequest(
        @NotNull
        Long terminalId,

        @NotBlank
        @Size(max = 80)
        String name
) {
}
