package com.sisuz.pos.domain.cash.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PosCashDrawerCreateRequest(
        @NotNull
        Long terminalId,

        @NotBlank
        @Size(max = 30)
        String code,

        @NotBlank
        @Size(max = 80)
        String name
) {
}
