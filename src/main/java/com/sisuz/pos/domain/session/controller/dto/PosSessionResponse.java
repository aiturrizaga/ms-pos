package com.sisuz.pos.domain.session.controller.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PosSessionResponse(
        Long id,
        Long terminalId,
        Long drawerId,
        String openedBy,
        Instant openedAt,
        String closedBy,
        Instant closedAt,
        String status,
        String openingNote,
        String closingNote,
        BigDecimal expectedTotalAmount,
        BigDecimal countedTotalAmount,
        BigDecimal diffTotalAmount,
        BigDecimal totalSale,
        Long transactionQty
) {
}
