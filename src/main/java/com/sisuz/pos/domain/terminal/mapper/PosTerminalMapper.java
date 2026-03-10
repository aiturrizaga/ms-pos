package com.sisuz.pos.domain.terminal.mapper;

import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalCreateRequest;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalResponse;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalUpdateRequest;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PosTerminalMapper {

    PosTerminal toEntity(PosTerminalCreateRequest request);

    PosTerminalResponse toResponse(PosTerminal entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PosTerminalUpdateRequest request, @MappingTarget PosTerminal entity);
}
