package com.sisuz.pos.domain.cash.service;

import com.sisuz.pos.domain.cash.controller.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PosCashDrawerService {
    PosCashDrawerResponse create(PosCashDrawerCreateRequest request);

    PosCashDrawerResponse update(Long id, PosCashDrawerUpdateRequest request);

    PosCashDrawerResponse getById(Long id);

    void activate(Long id);

    void deactivate(Long id);

    Page<PosCashDrawerResponse> getAllByTerminalId(PosCashDrawerFilter filter, Pageable pageable);

    PosCashDrawerUserResponse getByUserAndCompany();
}
