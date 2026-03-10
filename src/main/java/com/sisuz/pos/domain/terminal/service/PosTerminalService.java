package com.sisuz.pos.domain.terminal.service;

import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalCreateRequest;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalFilter;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalResponse;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PosTerminalService {

    PosTerminalResponse create(PosTerminalCreateRequest request);

    PosTerminalResponse update(Long id, PosTerminalUpdateRequest request);

    void activate(Long id);

    void deactivate(Long id);

    Page<PosTerminalResponse> getAll(PosTerminalFilter filter, Pageable pageable);
}
