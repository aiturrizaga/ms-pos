package com.sisuz.pos.domain.cash.repository.spec;

import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerFilter;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PosCashDrawerSpecFilter {

    Long terminalId;
    Boolean active;
    String q;
    String code;
    String name;

    public static PosCashDrawerSpecFilter from(PosCashDrawerFilter filter) {
        if (filter == null) {
            return PosCashDrawerSpecFilter.builder().build();
        }

        return PosCashDrawerSpecFilter.builder()
                .terminalId(filter.terminalId())
                .active(filter.active())
                .q(filter.q())
                .code(filter.code())
                .name(filter.name())
                .build();
    }
}
