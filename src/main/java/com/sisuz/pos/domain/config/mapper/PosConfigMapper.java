package com.sisuz.pos.domain.config.mapper;

import com.sisuz.pos.domain.config.controller.dto.PosConfigCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosConfigResponse;
import com.sisuz.pos.domain.config.controller.dto.PosConfigUpdateRequest;
import com.sisuz.pos.domain.config.entity.PosConfig;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PosConfigMapper {

    PosConfig toEntity(PosConfigCreateRequest request);

    PosConfigResponse toResponse(PosConfig entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PosConfigUpdateRequest request, @MappingTarget PosConfig entity);
}
