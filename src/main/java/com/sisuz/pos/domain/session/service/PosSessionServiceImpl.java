package com.sisuz.pos.domain.session.service;

import com.sisuz.pos.common.exception.BusinessException;
import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.session.controller.dto.PosSessionCloseRequest;
import com.sisuz.pos.domain.session.controller.dto.PosSessionFilter;
import com.sisuz.pos.domain.session.controller.dto.PosSessionOpenRequest;
import com.sisuz.pos.domain.session.controller.dto.PosSessionResponse;
import com.sisuz.pos.domain.session.entity.PosSession;
import com.sisuz.pos.domain.session.entity.PosSessionStatus;
import com.sisuz.pos.domain.session.mapper.PosSessionMapper;
import com.sisuz.pos.domain.session.repository.PosSessionRepository;
import com.sisuz.pos.domain.session.repository.spec.PosSessionSpecFilter;
import com.sisuz.pos.domain.session.repository.spec.PosSessionSpecs;
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

        return mapper.toResponse(session);
    }

    @Override
    public Page<PosSessionResponse> getAll(PosSessionFilter filter, Pageable pageable) {

        Specification<PosSession> spec =
                PosSessionSpecs.withFilters(PosSessionSpecFilter.from(filter));

        return sessionRepository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}
