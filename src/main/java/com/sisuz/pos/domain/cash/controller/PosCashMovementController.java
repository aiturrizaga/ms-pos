package com.sisuz.pos.domain.cash.controller;

import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementCreateRequest;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementFilter;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementResponse;
import com.sisuz.pos.domain.cash.controller.dto.PosCashMovementVoidRequest;
import com.sisuz.pos.domain.cash.service.PosCashMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cash-movements")
@RequiredArgsConstructor
public class PosCashMovementController {

    private final PosCashMovementService service;

    @PostMapping
    public PosCashMovementResponse create(@RequestBody @Valid PosCashMovementCreateRequest request) {
        return service.create(request);
    }

    @PostMapping("/{id}/void")
    public PosCashMovementResponse voidMovement(
            @PathVariable Long id,
            @RequestBody @Valid PosCashMovementVoidRequest request
    ) {
        if (!id.equals(request.id())) {
            throw new com.sisuz.pos.common.exception.BusinessException(4211, "Path variable id does not match request id");
        }
        return service.voidMovement(request);
    }

    @GetMapping("/{id}")
    public PosCashMovementResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<PosCashMovementResponse> getAll(
            PosCashMovementFilter filter,
            @PageableDefault(size = 20, sort = "occurredAt") Pageable pageable
    ) {
        return service.getAll(filter, pageable);
    }
}
