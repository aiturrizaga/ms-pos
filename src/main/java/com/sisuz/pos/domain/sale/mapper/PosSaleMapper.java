package com.sisuz.pos.domain.sale.mapper;

import com.sisuz.pos.domain.sale.controller.dto.PosSaleLineResponse;
import com.sisuz.pos.domain.sale.controller.dto.PosSalePaymentResponse;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleResponse;
import com.sisuz.pos.domain.sale.entity.PosSale;
import com.sisuz.pos.domain.sale.entity.PosSaleLine;
import com.sisuz.pos.domain.sale.entity.PosSalePayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PosSaleMapper {

    @Mapping(target = "sessionId", source = "session.id")
    @Mapping(target = "terminalId", source = "terminal.id")
    PosSaleResponse toResponse(PosSale entity);

    PosSaleLineResponse toLineResponse(PosSaleLine entity);

    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "paymentMethodCode", source = "paymentMethod.code")
    @Mapping(target = "paymentMethodName", source = "paymentMethod.name")
    PosSalePaymentResponse toPaymentResponse(PosSalePayment entity);
}
