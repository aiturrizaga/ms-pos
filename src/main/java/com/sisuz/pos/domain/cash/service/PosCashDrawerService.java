package com.sisuz.pos.domain.cash.service;

import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerCreateRequest;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerFilter;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerResponse;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PosCashDrawerService {
    PosCashDrawerResponse create(PosCashDrawerCreateRequest request);

    PosCashDrawerResponse update(Long id, PosCashDrawerUpdateRequest request);

    PosCashDrawerResponse getById(Long id);

    void activate(Long id);

    void deactivate(Long id);

    Page<PosCashDrawerResponse> getAllByTerminalId(PosCashDrawerFilter filter, Pageable pageable);
}
