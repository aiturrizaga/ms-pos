package com.sisuz.pos.domain.sale.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PosSaleLineCreateRequest(

        @NotNull
        Long skuId,

        @NotBlank
        String skuName,

        @NotNull
        BigDecimal qty,

        @NotNull
        BigDecimal unitPrice,

        @NotNull
        BigDecimal discountAmount,

        @NotNull
        BigDecimal taxAmount,

        @NotNull
        BigDecimal lineTotal,

        @Size(max = 255)
        String note

) {
}
