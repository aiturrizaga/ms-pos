package com.sisuz.pos.domain.sale.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PosSalePaymentCreateRequest(

        @NotNull
        Long paymentMethodId,

        @NotNull
        BigDecimal amount,

        @NotBlank
        @Size(max = 3)
        String currencyCode,

        @Size(max = 80)
        String reference,

        @NotBlank
        @Size(max = 80)
        String receivedBy

) {
}
