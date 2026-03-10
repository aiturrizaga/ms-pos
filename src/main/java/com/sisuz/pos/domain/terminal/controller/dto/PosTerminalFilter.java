package com.sisuz.pos.domain.terminal.controller.dto;

public record PosTerminalFilter(
        Boolean active,
        String q,
        String code,
        String name,
        Long storeId
) {
}