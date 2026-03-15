package com.sisuz.pos.domain.sale.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record PosSaleCreateRequest(

        @NotNull
        Long sessionId,

        @NotNull
        Long terminalId,

        Long customerId,

        @NotBlank
        @Size(max = 3)
        String currencyCode,

        @NotNull
        BigDecimal subtotal,

        @NotNull
        BigDecimal discountTotal,

        @NotNull
        BigDecimal taxTotal,

        @NotNull
        BigDecimal total,

        @NotBlank
        @Size(max = 80)
        String cashierId,

        @Size(max = 255)
        String note,

        String documentType,

        @Valid
        @NotEmpty
        List<PosSaleLineCreateRequest> lines,

        @Valid
        @NotEmpty
        List<PosSalePaymentCreateRequest> payments

) {
}
