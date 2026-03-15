package com.sisuz.pos.domain.sale.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PosSaleLineResponse(
        Long id,
        UUID skuId,
        String skuName,
        BigDecimal qty,
        BigDecimal unitPrice,
        BigDecimal discountAmount,
        BigDecimal taxAmount,
        BigDecimal lineTotal,
        String note
) {
}
