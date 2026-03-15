package com.sisuz.pos.domain.session.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PosSessionCloseRequest(

        @NotNull
        Long id,

        @NotNull
        @Size(max = 80)
        String closedBy,

        BigDecimal countedTotalAmount,

        String closingNote

) {
}
