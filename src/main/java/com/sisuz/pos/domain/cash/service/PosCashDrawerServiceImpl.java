package com.sisuz.pos.domain.cash.service;

import com.sisuz.pos.common.exception.BusinessException;
import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.cash.controller.dto.*;
import com.sisuz.pos.domain.cash.entity.PosCashDrawer;
import com.sisuz.pos.domain.cash.mapper.PosCashDrawerMapper;
import com.sisuz.pos.domain.cash.repository.PosCashDrawerRepository;
import com.sisuz.pos.domain.cash.repository.spec.PosCashDrawerSpecFilter;
import com.sisuz.pos.domain.cash.repository.spec.PosCashDrawerSpecs;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import com.sisuz.pos.domain.terminal.repository.PosTerminalRepository;
import com.sisuz.pos.security.context.ContextAwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PosCashDrawerServiceImpl extends ContextAwareService implements PosCashDrawerService {

    private final PosCashDrawerRepository cashDrawerRepository;
    private final PosTerminalRepository terminalRepository;
    private final PosCashDrawerMapper cashDrawerMapper;

    @Override
    public PosCashDrawerResponse create(PosCashDrawerCreateRequest request) {
        boolean exists = cashDrawerRepository.existsByCodeIgnoreCase(request.code());

        if (exists) {
            throw new BusinessException("Cash drawer code already exists: " + request.code());
        }

        PosTerminal terminal = terminalRepository.findById(request.terminalId())
                .orElseThrow(() -> new RuntimeException("POS terminal not found"));

        PosCashDrawer entity = cashDrawerMapper.toEntity(request);
        entity.setTerminal(terminal);

        return cashDrawerMapper.toResponse(cashDrawerRepository.save(entity));
    }

    @Override
    public PosCashDrawerResponse update(Long id, PosCashDrawerUpdateRequest request) {
        PosCashDrawer entity = cashDrawerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cash drawer not found"));

        PosTerminal terminal = terminalRepository.findById(request.terminalId())
                .orElseThrow(() -> new NotFoundException("POS terminal not found"));

        cashDrawerMapper.update(request, entity);
        entity.setTerminal(terminal);

        return cashDrawerMapper.toResponse(cashDrawerRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public PosCashDrawerResponse getById(Long id) {
        PosCashDrawer entity = cashDrawerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cash drawer not found"));

        return cashDrawerMapper.toResponse(entity);
    }

    @Override
    public void activate(Long id) {
        PosCashDrawer entity = cashDrawerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cash drawer not found"));

        entity.setActive(true);
        cashDrawerRepository.save(entity);
    }

    @Override
    public void deactivate(Long id) {
        PosCashDrawer entity = cashDrawerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cash drawer not found"));

        entity.setActive(false);
        cashDrawerRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PosCashDrawerResponse> getAllByTerminalId(PosCashDrawerFilter filter, Pageable pageable) {
        PosCashDrawerSpecFilter specFilter = PosCashDrawerSpecFilter.from(filter);
        Specification<PosCashDrawer> spec = PosCashDrawerSpecs.withFilters(specFilter);

        return cashDrawerRepository.findAll(spec, pageable)
                .map(cashDrawerMapper::toResponse);
    }

    @Override
    public PosCashDrawerUserResponse getByUserAndCompany() {
        PosCashDrawer cashDrawer = cashDrawerRepository.findFirstByTerminalCompanyId(currentCompanyId())
                .orElseThrow(() -> new BusinessException("Cash drawer not configured for this company or user"));
        return new PosCashDrawerUserResponse(
                cashDrawer.getTerminal().getId(),
                cashDrawer.getTerminal().getName(),
                cashDrawer.getId(),
                cashDrawer.getName()
        );
    }
}
