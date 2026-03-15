package com.sisuz.pos.domain.cash.mapper;

import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementResponse;
import com.sisuz.pos.domain.cash.entity.PosCashMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PosCashMovementMapper {

    @Mapping(target = "sessionId", source = "session.id")
    @Mapping(target = "terminalId", source = "terminal.id")
    @Mapping(target = "drawerId", source = "drawer.id")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "paymentMethodCode", source = "paymentMethod.code")
    @Mapping(target = "paymentMethodName", source = "paymentMethod.name")
    PosCashMovementResponse toResponse(PosCashMovement entity);
}
