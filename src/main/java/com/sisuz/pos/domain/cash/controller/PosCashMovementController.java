package com.sisuz.pos.domain.cash.controller;

import com.sisuz.pos.common.api.ApiResponse;
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
    public ApiResponse<PosCashMovementResponse> create(@RequestBody @Valid PosCashMovementCreateRequest request) {
        return ApiResponse.success(service.create(request));
    }

    @PostMapping("/void")
    public ApiResponse<PosCashMovementResponse> voidMovement(
            @RequestBody @Valid PosCashMovementVoidRequest request
    ) {
        return ApiResponse.success(service.voidMovement(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<PosCashMovementResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(service.getById(id));
    }

    @GetMapping
    public ApiResponse<Page<PosCashMovementResponse>> getAll(
            PosCashMovementFilter filter,
            @PageableDefault(size = 20, sort = "occurredAt") Pageable pageable
    ) {
        return ApiResponse.success(service.getAll(filter, pageable));
    }
}
