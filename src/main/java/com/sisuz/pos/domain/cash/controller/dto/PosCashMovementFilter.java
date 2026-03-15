package com.sisuz.pos.domain.cash.controller.dto;

import java.time.Instant;

public record PosCashMovementFilter(
        Long sessionId,
        Long terminalId,
        Long drawerId,
        String movementType,
        Long paymentMethodId,
        String currencyCode,
        Boolean isVoided,
        String reasonCode,
        Instant occurredFrom,
        Instant occurredTo,
        String q
) {
}
