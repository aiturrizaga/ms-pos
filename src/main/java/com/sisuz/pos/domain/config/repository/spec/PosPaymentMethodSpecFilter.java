package com.sisuz.pos.domain.config.repository.spec;

import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodFilter;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PosPaymentMethodSpecFilter {

    Boolean active;
    String q;
    String code;
    String name;
    String methodType;
    Boolean requiresReference;

    public static PosPaymentMethodSpecFilter from(PosPaymentMethodFilter filter) {
        if (filter == null) {
            return PosPaymentMethodSpecFilter.builder().build();
        }

        return PosPaymentMethodSpecFilter.builder()
                .active(filter.active())
                .q(filter.q())
                .code(filter.code())
                .name(filter.name())
                .methodType(filter.methodType())
                .requiresReference(filter.requiresReference())
                .build();
    }
}
