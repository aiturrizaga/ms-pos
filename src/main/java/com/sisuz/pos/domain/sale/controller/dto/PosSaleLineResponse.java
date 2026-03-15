package com.sisuz.pos.domain.sale.controller.dto;

import java.math.BigDecimal;

public record PosSaleLineResponse(
        Long id,
        Long skuId,
        String skuName,
        BigDecimal qty,
        BigDecimal unitPrice,
        BigDecimal discountAmount,
        BigDecimal taxAmount,
        BigDecimal lineTotal,
        String note
) {
}
