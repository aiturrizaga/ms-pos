package com.sisuz.pos.domain.session.mapper;

import com.sisuz.pos.domain.session.controller.dto.PosSessionResponse;
import com.sisuz.pos.domain.session.entity.PosSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PosSessionMapper {

    @Mapping(target = "terminalId", source = "terminal.id")
    @Mapping(target = "drawerId", source = "drawer.id")
    PosSessionResponse toResponse(PosSession entity);

}
