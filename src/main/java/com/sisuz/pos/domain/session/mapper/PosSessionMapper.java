package com.sisuz.pos.domain.session.mapper;

import com.sisuz.pos.domain.session.controller.dto.PosSessionResponse;
import com.sisuz.pos.domain.session.controller.dto.PosSessionSaleStats;
import com.sisuz.pos.domain.session.entity.PosSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PosSessionMapper {

    @Mapping(target = "terminalId", source = "terminal.id")
    @Mapping(target = "drawerId", source = "drawer.id")
    @Mapping(target = "totalSale", ignore = true)
    @Mapping(target = "transactionQty", ignore = true)
    PosSessionResponse toResponse(PosSession entity);

    default PosSessionResponse toResponse(PosSession entity, PosSessionSaleStats stats) {
        PosSessionResponse base = toResponse(entity);
        return new PosSessionResponse(
                base.id(), base.terminalId(), base.drawerId(),
                base.openedBy(), base.openedAt(), base.closedBy(), base.closedAt(),
                base.status(), base.openingNote(), base.closingNote(),
                base.expectedTotalAmount(), base.countedTotalAmount(), base.diffTotalAmount(),
                stats.totalSale(), stats.transactionQty()
        );
    }

}
