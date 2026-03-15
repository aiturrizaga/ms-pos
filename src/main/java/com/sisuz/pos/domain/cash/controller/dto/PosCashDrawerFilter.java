package com.sisuz.pos.domain.cash.controller.dto;

public record PosCashDrawerFilter(
        Long terminalId,
        Boolean active,
        String q,
        String code,
        String name
) {
}
