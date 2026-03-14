package com.sisuz.pos.domain.config.mapper;

import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodResponse;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodUpdateRequest;
import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PosPaymentMethodMapper {

    PosPaymentMethod toEntity(PosPaymentMethodCreateRequest request);

    PosPaymentMethodResponse toResponse(PosPaymentMethod entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PosPaymentMethodUpdateRequest request, @MappingTarget PosPaymentMethod entity);
}
