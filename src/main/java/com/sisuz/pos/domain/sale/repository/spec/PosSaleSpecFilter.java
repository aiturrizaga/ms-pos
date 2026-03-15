package com.sisuz.pos.domain.sale.repository.spec;

import com.sisuz.pos.domain.sale.controller.dto.PosSaleFilter;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class PosSaleSpecFilter {

    Long sessionId;
    Long terminalId;
    Long customerId;
    String status;
    String currencyCode;
    String cashierId;
    String documentType;
    Instant paidFrom;
    Instant paidTo;
    String q;

    public static PosSaleSpecFilter from(PosSaleFilter filter) {
        if (filter == null) {
            return PosSaleSpecFilter.builder().build();
        }

        return PosSaleSpecFilter.builder()
                .sessionId(filter.sessionId())
                .terminalId(filter.terminalId())
                .customerId(filter.customerId())
                .status(filter.status())
                .currencyCode(filter.currencyCode())
                .cashierId(filter.cashierId())
                .documentType(filter.documentType())
                .paidFrom(filter.paidFrom())
                .paidTo(filter.paidTo())
                .q(filter.q())
                .build();
    }
}
