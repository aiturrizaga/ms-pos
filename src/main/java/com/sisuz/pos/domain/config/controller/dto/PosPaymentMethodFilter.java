package com.sisuz.pos.domain.config.controller.dto;

public record PosPaymentMethodFilter(
        Boolean active,
        String q,
        String code,
        String name,
        String methodType,
        Boolean requiresReference
) {
}
