package com.sisuz.pos.domain.sale.controller.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PosSaleResponse(
        Long id,
        Long sessionId,
        Long terminalId,
        String saleNumber,
        Long customerId,
        String status,
        String currencyCode,
        BigDecimal total,
        Instant paidAt,
        String cashierId,
        String documentType,
        Long documentId
) {
}
