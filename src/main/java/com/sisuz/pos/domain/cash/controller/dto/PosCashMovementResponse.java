package com.sisuz.pos.domain.cash.controller.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PosCashMovementResponse(
        Long id,
        Long sessionId,
        Long terminalId,
        Long drawerId,
        String movementType,
        Long paymentMethodId,
        String paymentMethodCode,
        String paymentMethodName,
        BigDecimal amount,
        String currencyCode,
        Instant occurredAt,
        String reasonCode,
        String note,
        Boolean isVoided,
        Instant voidedAt,
        String voidedBy
) {
}
