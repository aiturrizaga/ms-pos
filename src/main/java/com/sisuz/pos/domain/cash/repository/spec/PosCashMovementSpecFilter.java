package com.sisuz.pos.domain.cash.repository.spec;

import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementFilter;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class PosCashMovementSpecFilter {

    Long sessionId;
    Long terminalId;
    Long drawerId;
    String movementType;
    Long paymentMethodId;
    String currencyCode;
    Boolean isVoided;
    String reasonCode;
    Instant occurredFrom;
    Instant occurredTo;
    String q;

    public static PosCashMovementSpecFilter from(PosCashMovementFilter filter) {
        if (filter == null) {
            return PosCashMovementSpecFilter.builder().build();
        }

        return PosCashMovementSpecFilter.builder()
                .sessionId(filter.sessionId())
                .terminalId(filter.terminalId())
                .drawerId(filter.drawerId())
                .movementType(filter.movementType())
                .paymentMethodId(filter.paymentMethodId())
                .currencyCode(filter.currencyCode())
                .isVoided(filter.isVoided())
                .reasonCode(filter.reasonCode())
                .occurredFrom(filter.occurredFrom())
                .occurredTo(filter.occurredTo())
                .q(filter.q())
                .build();
    }
}
