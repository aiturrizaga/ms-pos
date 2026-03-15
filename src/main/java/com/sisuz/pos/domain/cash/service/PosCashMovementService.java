package com.sisuz.pos.domain.cash.service;

import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementCreateRequest;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementFilter;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementResponse;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementVoidRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PosCashMovementService {

    PosCashMovementResponse create(PosCashMovementCreateRequest request);

    PosCashMovementResponse voidMovement(PosCashMovementVoidRequest request);

    PosCashMovementResponse getById(Long id);

    Page<PosCashMovementResponse> getAll(PosCashMovementFilter filter, Pageable pageable);
}
