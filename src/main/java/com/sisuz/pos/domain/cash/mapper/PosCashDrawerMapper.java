package com.sisuz.pos.domain.cash.mapper;

import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerCreateRequest;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerResponse;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerUpdateRequest;
import com.sisuz.pos.domain.cash.entity.PosCashDrawer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PosCashDrawerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "terminal", ignore = true)
    @Mapping(target = "active", ignore = true)
    PosCashDrawer toEntity(PosCashDrawerCreateRequest request);

    @Mapping(target = "terminalId", source = "terminal.id")
    PosCashDrawerResponse toResponse(PosCashDrawer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "terminal", ignore = true)
    @Mapping(target = "active", ignore = true)
    void update(PosCashDrawerUpdateRequest request, @MappingTarget PosCashDrawer entity);
}
