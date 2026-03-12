package com.sisuz.pos.domain.session.controller.dto;

import java.time.Instant;

public record PosSessionFilter(
        Long terminalId,
        Long drawerId,
        String status,
        String openedBy,
        Instant openedFrom,
        Instant openedTo
) {
}
