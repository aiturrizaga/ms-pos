package com.sisuz.pos.domain.config.controller.dto;

public record PosPaymentMethodResponse(
        Long id,
        String code,
        String name,
        String methodType,
        Boolean requiresReference,
        boolean active
) {
}
