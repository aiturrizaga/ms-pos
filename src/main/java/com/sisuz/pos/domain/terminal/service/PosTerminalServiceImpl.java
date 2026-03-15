package com.sisuz.pos.domain.terminal.service;

import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalCreateRequest;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalFilter;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalResponse;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalUpdateRequest;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import com.sisuz.pos.domain.terminal.mapper.PosTerminalMapper;
import com.sisuz.pos.domain.terminal.repository.PosTerminalRepository;
import com.sisuz.pos.domain.terminal.repository.spec.PosTerminalSpecFilter;
import com.sisuz.pos.domain.terminal.repository.spec.PosTerminalSpecs;
import com.sisuz.pos.security.context.ContextAwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PosTerminalServiceImpl extends ContextAwareService implements PosTerminalService {

    private final PosTerminalRepository terminalRepository;
    private final PosTerminalMapper terminalMapper;

    @Override
    public PosTerminalResponse create(PosTerminalCreateRequest request) {

        PosTerminal entity = terminalMapper.toEntity(request);

        entity.setCompanyId(currentCompanyId());
        entity.setActive(true);

        return terminalMapper.toResponse(terminalRepository.save(entity));
    }

    @Override
    public PosTerminalResponse update(Long id, PosTerminalUpdateRequest request) {

        PosTerminal entity = terminalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Terminal not found"));

        terminalMapper.update(request, entity);

        return terminalMapper.toResponse(terminalRepository.save(entity));
    }

    @Override
    public void activate(Long id) {

        PosTerminal entity = terminalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Terminal not found"));

        entity.setActive(true);

        terminalRepository.save(entity);
    }

    @Override
    public void deactivate(Long id) {

        PosTerminal entity = terminalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Terminal not found"));

        entity.setActive(false);

        terminalRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PosTerminalResponse> getAll(PosTerminalFilter filter, Pageable pageable) {

        UUID companyId = currentCompanyId();

        PosTerminalSpecFilter specFilter = PosTerminalSpecFilter.from(filter, companyId);

        Specification<PosTerminal> spec = PosTerminalSpecs.withFilters(specFilter);

        return terminalRepository.findAll(spec, pageable)
                .map(terminalMapper::toResponse);
    }
}
