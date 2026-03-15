package com.sisuz.pos.domain.cash.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PosCashMovementVoidRequest(
        @NotNull
        Long id,

        @NotBlank
        @Size(max = 80)
        String voidedBy,

        @Size(max = 255)
        String note
) {
}
