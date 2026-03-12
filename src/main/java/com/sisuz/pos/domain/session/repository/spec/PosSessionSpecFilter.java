package com.sisuz.pos.domain.session.repository.spec;

import com.sisuz.pos.domain.session.controller.dto.PosSessionFilter;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class PosSessionSpecFilter {

    Long terminalId;
    Long drawerId;
    String status;
    String openedBy;
    Instant openedFrom;
    Instant openedTo;

    public static PosSessionSpecFilter from(PosSessionFilter filter) {

        if (filter == null) return PosSessionSpecFilter.builder().build();

        return PosSessionSpecFilter.builder()
                .terminalId(filter.terminalId())
                .drawerId(filter.drawerId())
                .status(filter.status())
                .openedBy(filter.openedBy())
                .openedFrom(filter.openedFrom())
                .openedTo(filter.openedTo())
                .build();
    }
}