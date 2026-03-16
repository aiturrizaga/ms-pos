package com.sisuz.pos.domain.session.service;

import com.sisuz.pos.common.exception.BusinessException;
import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.cash.entity.PosCashMovement;
import com.sisuz.pos.domain.cash.repository.PosCashMovementRepository;
import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import com.sisuz.pos.domain.config.repository.PosPaymentMethodRepository;
import com.sisuz.pos.domain.sale.entity.PosSale;
import com.sisuz.pos.domain.sale.entity.PosSalePayment;
import com.sisuz.pos.domain.sale.repository.PosSaleRepository;
import com.sisuz.pos.domain.session.controller.dto.*;
import com.sisuz.pos.domain.session.entity.PosSession;
import com.sisuz.pos.domain.session.entity.PosSessionStatus;
import com.sisuz.pos.domain.session.mapper.PosSessionMapper;
import com.sisuz.pos.domain.session.repository.PosSessionRepository;
import com.sisuz.pos.domain.session.repository.spec.PosSessionSpecFilter;
import com.sisuz.pos.domain.session.repository.spec.PosSessionSpecs;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import com.sisuz.pos.domain.terminal.repository.PosTerminalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PosSessionServiceImpl implements PosSessionService {

    private final PosSessionRepository sessionRepository;
    private final PosTerminalRepository terminalRepository;
    private final PosSaleRepository saleRepository;
    private final PosPaymentMethodRepository paymentMethodRepository;
    private final PosCashMovementRepository cashMovementRepository;
    private final PosSessionMapper mapper;

    @Override
    public PosSessionResponse open(PosSessionOpenRequest request) {

        sessionRepository.findFirstByTerminalIdAndStatus(
                request.terminalId(),
                PosSessionStatus.OPEN.name()
        ).ifPresent(s -> {
            throw new BusinessException("Terminal already has an open session");
        });

        PosSession session = new PosSession();

        session.setTerminal(
                terminalRepository.getReferenceById(request.terminalId())
        );

        session.setOpenedBy(request.openedBy());
        session.setOpenedAt(Instant.now());
        session.setStatus(PosSessionStatus.OPEN.name());
        session.setOpeningNote(request.openingNote());
        session.setExpectedTotalAmount(request.expectedTotalAmount());

        sessionRepository.save(session);
        cashMovementRepository.save(buildCashMovementFromSession(session));

        return mapper.toResponse(session);
    }

    @Override
    public PosSessionResponse close(PosSessionCloseRequest request) {

        PosSession session = sessionRepository.findById(request.id())
                .orElseThrow(() -> new NotFoundException("Session not found"));

        if (!PosSessionStatus.OPEN.name().equals(session.getStatus())) {
            throw new BusinessException("Session already closed");
        }

        session.setClosedBy(request.closedBy());
        session.setClosedAt(Instant.now());
        session.setClosingNote(request.closingNote());
        session.setCountedTotalAmount(request.countedTotalAmount());
        session.setStatus(PosSessionStatus.CLOSED.name());

        BigDecimal expected = session.getExpectedTotalAmount();
        BigDecimal counted = request.countedTotalAmount();

        if (expected != null && counted != null) {
            session.setDiffTotalAmount(counted.subtract(expected));
        }

        sessionRepository.save(session);

        return mapper.toResponse(session);
    }

    @Override
    public PosSessionResponse getCurrentSession(Long terminalId) {

        PosSession session = sessionRepository
                .findFirstByTerminalIdAndStatus(terminalId, PosSessionStatus.OPEN.name())
                .orElseThrow(() -> new BusinessException("No open session"));

        PosSaleStatsProjection stats = saleRepository.getSaleStatsBySessionId(session.getId());
        return mapper.toResponse(session, PosSessionSaleStats.from(stats));
    }

    @Override
    public Page<PosSessionResponse> getAll(PosSessionFilter filter, Pageable pageable) {

        Specification<PosSession> spec =
                PosSessionSpecs.withFilters(PosSessionSpecFilter.from(filter));

        return sessionRepository.findAll(spec, pageable)
                .map(session -> {
                    PosSaleStatsProjection stats = saleRepository.getSaleStatsBySessionId(session.getId());
                    return mapper.toResponse(session, PosSessionSaleStats.from(stats));
                });
    }

    private PosCashMovement buildCashMovementFromSession(PosSession session) {
        PosPaymentMethod paymentMethod = paymentMethodRepository.findById(1L)
                .orElseThrow(() -> new BusinessException("Cash payment method not found"));
        PosCashMovement movement = new PosCashMovement();
        movement.setSession(session);
        movement.setTerminal(session.getTerminal());
        movement.setDrawer(session.getDrawer());
        movement.setMovementType("OPEN_CASH");
        movement.setPaymentMethod(paymentMethod);
        movement.setAmount(session.getExpectedTotalAmount());
        movement.setCurrencyCode("PEN");
        movement.setOccurredAt(session.getCreatedDate());
        movement.setReasonCode("OPEN_CASH");
        movement.setNote("Open cash");
        movement.setIsVoided(false);
        return movement;
    }
}
