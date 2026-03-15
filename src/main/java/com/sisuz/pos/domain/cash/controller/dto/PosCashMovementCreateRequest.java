package com.sisuz.pos.domain.cash.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record PosCashMovementCreateRequest(

        @NotNull
        Long sessionId,

        @NotNull
        Long terminalId,

        Long drawerId,

        @NotBlank
        @Size(max = 30)
        String movementType,

        @NotNull
        Long paymentMethodId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount,

        @NotBlank
        @Size(max = 3)
        String currencyCode,

        Instant occurredAt,

        @Size(max = 30)
        String reasonCode,

        @Size(max = 255)
        String note

) {
}
