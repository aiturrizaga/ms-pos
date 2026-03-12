package com.sisuz.pos.domain.session.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PosSessionOpenRequest(

        @NotNull
        Long terminalId,

        Long drawerId,

        @NotNull
        @Size(max = 80)
        String openedBy,

        BigDecimal expectedTotalAmount,

        String openingNote

) {
}