package com.sisuz.pos.domain.sale.controller.dto;

import java.time.Instant;

public record PosSaleFilter(
        Long sessionId,
        Long terminalId,
        Long customerId,
        String status,
        String currencyCode,
        String cashierId,
        String documentType,
        Instant paidFrom,
        Instant paidTo,
        String q
) {
}
