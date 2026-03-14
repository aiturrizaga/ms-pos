package com.sisuz.pos.domain.config.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PosPaymentMethodUpdateRequest(

        @NotBlank
        @Size(max = 60)
        String name,

        @NotBlank
        @Size(max = 30)
        String methodType,

        @NotNull
        Boolean requiresReference
) {
}
