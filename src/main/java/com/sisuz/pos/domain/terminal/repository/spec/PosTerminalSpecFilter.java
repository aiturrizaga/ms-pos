package com.sisuz.pos.domain.terminal.repository.spec;

import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalFilter;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class PosTerminalSpecFilter {

    UUID companyId;
    Boolean active;
    String q;
    String code;
    String name;
    Long storeId;

    public static PosTerminalSpecFilter from(
            PosTerminalFilter filter,
            UUID companyId
    ) {
        if (filter == null) {
            return PosTerminalSpecFilter.builder()
                    .companyId(companyId)
                    .build();
        }

        return PosTerminalSpecFilter.builder()
                .companyId(companyId)
                .active(filter.active())
                .q(filter.q())
                .code(filter.code())
                .name(filter.name())
                .storeId(filter.storeId())
                .build();
    }
}