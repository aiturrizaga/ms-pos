package com.sisuz.pos.domain.terminal.controller.dto;

import java.util.UUID;

public record PosTerminalResponse(
        Long id,
        UUID companyId,
        Long storeId,
        String code,
        String name,
        boolean active
) {
}
