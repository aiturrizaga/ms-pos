package com.sisuz.pos.domain.cash.controller.dto;

public record PosCashDrawerResponse(
        Long id,
        Long terminalId,
        String code,
        String name,
        boolean active
) {
}
