package com.sisuz.pos.domain.cash.service;

import com.sisuz.pos.common.exception.BusinessException;
import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementCreateRequest;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementFilter;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementResponse;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementVoidRequest;
import com.sisuz.pos.domain.cash.entity.PosCashDrawer;
import com.sisuz.pos.domain.cash.entity.PosCashMovement;
import com.sisuz.pos.domain.cash.mapper.PosCashMovementMapper;
import com.sisuz.pos.domain.cash.repository.PosCashDrawerRepository;
import com.sisuz.pos.domain.cash.repository.PosCashMovementRepository;
import com.sisuz.pos.domain.cash.repository.spec.PosCashMovementSpecFilter;
import com.sisuz.pos.domain.cash.repository.spec.PosCashMovementSpecs;
import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import com.sisuz.pos.domain.config.repository.PosPaymentMethodRepository;
import com.sisuz.pos.domain.session.entity.PosSession;
import com.sisuz.pos.domain.session.repository.PosSessionRepository;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import com.sisuz.pos.domain.terminal.repository.PosTerminalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PosCashMovementServiceImpl implements PosCashMovementService {

    private static final String SESSION_OPEN = "OPEN";

    private final PosCashMovementRepository cashMovementRepository;
    private final PosSessionRepository sessionRepository;
    private final PosTerminalRepository terminalRepository;
    private final PosCashDrawerRepository drawerRepository;
    private final PosPaymentMethodRepository paymentMethodRepository;
    private final PosCashMovementMapper cashMovementMapper;

    @Override
    public PosCashMovementResponse create(PosCashMovementCreateRequest request) {
        PosSession session = sessionRepository.findById(request.sessionId())
                .orElseThrow(() -> NotFoundException.of("PosSession", request.sessionId()));

        PosTerminal terminal = terminalRepository.findById(request.terminalId())
                .orElseThrow(() -> NotFoundException.of("PosTerminal", request.terminalId()));

        PosPaymentMethod paymentMethod = paymentMethodRepository.findById(request.paymentMethodId())
                .orElseThrow(() -> NotFoundException.of("PosPaymentMethod", request.paymentMethodId()));

        PosCashDrawer drawer = null;
        if (request.drawerId() != null) {
            drawer = drawerRepository.findById(request.drawerId())
                    .orElseThrow(() -> NotFoundException.of("PosCashDrawer", request.drawerId()));
        }

        validateCreateBusinessRules(session, terminal, drawer, paymentMethod, request);

        PosCashMovement entity = new PosCashMovement();
        entity.setSession(session);
        entity.setTerminal(terminal);
        entity.setDrawer(drawer);
        entity.setMovementType(request.movementType().trim());
        entity.setPaymentMethod(paymentMethod);
        entity.setAmount(request.amount());
        entity.setCurrencyCode(request.currencyCode().trim().toUpperCase());
        entity.setOccurredAt(request.occurredAt() != null ? request.occurredAt() : Instant.now());
        entity.setReasonCode(trimToNull(request.reasonCode()));
        entity.setNote(trimToNull(request.note()));
        entity.setIsVoided(false);
        entity.setVoidedAt(null);
        entity.setVoidedBy(null);

        return cashMovementMapper.toResponse(cashMovementRepository.save(entity));
    }

    @Override
    public PosCashMovementResponse voidMovement(PosCashMovementVoidRequest request) {
        PosCashMovement entity = cashMovementRepository.findById(request.id())
                .orElseThrow(() -> NotFoundException.of("PosCashMovement", request.id()));

        if (Boolean.TRUE.equals(entity.getIsVoided())) {
            throw new BusinessException("Cash movement is already voided");
        }

        entity.setIsVoided(true);
        entity.setVoidedAt(Instant.now());
        entity.setVoidedBy(request.voidedBy());

        String extraNote = trimToNull(request.note());
        if (extraNote != null) {
            String base = entity.getNote() == null ? "" : entity.getNote().trim();
            entity.setNote(base.isBlank() ? "[VOID] " + extraNote : base + " | [VOID] " + extraNote);
        }

        return cashMovementMapper.toResponse(cashMovementRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public PosCashMovementResponse getById(Long id) {
        PosCashMovement entity = cashMovementRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("PosCashMovement", id));

        return cashMovementMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PosCashMovementResponse> getAll(PosCashMovementFilter filter, Pageable pageable) {
        PosCashMovementSpecFilter specFilter = PosCashMovementSpecFilter.from(filter);
        Specification<PosCashMovement> spec = PosCashMovementSpecs.withFilters(specFilter);

        return cashMovementRepository.findAll(spec, pageable)
                .map(cashMovementMapper::toResponse);
    }

    private void validateCreateBusinessRules(
            PosSession session,
            PosTerminal terminal,
            PosCashDrawer drawer,
            PosPaymentMethod paymentMethod,
            PosCashMovementCreateRequest request
    ) {
        if (!SESSION_OPEN.equalsIgnoreCase(session.getStatus())) {
            throw new BusinessException(4202, "Cash movement can only be created for an OPEN session");
        }

        if (!session.getTerminal().getId().equals(terminal.getId())) {
            throw new BusinessException(4203, "Session does not belong to the provided terminal");
        }

        if (drawer != null && drawer.getTerminal() != null && !drawer.getTerminal().getId().equals(terminal.getId())) {
            throw new BusinessException(4204, "Drawer does not belong to the provided terminal");
        }

        if (!terminal.isActive()) {
            throw new BusinessException(4205, "Terminal is inactive");
        }

        if (drawer != null && !drawer.isActive()) {
            throw new BusinessException(4206, "Drawer is inactive");
        }

        if (!paymentMethod.isActive()) {
            throw new BusinessException(4207, "Payment method is inactive");
        }

        if (request.amount() == null || request.amount().signum() <= 0) {
            throw new BusinessException(4208, "Amount must be greater than zero");
        }

        if (request.movementType() == null || request.movementType().isBlank()) {
            throw new BusinessException(4209, "Movement type is required");
        }

        if (request.currencyCode() == null || request.currencyCode().isBlank()) {
            throw new BusinessException(4210, "Currency code is required");
        }
    }

    private String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isBlank() ? null : trimmed;
    }
}
