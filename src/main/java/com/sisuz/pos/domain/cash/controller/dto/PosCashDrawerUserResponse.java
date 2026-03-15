package com.sisuz.pos.domain.cash.controller.dto;

public record PosCashDrawerUserResponse(
        Long terminalId,
        String terminalName,
        Long drawerId,
        String drawerName
) {
}
