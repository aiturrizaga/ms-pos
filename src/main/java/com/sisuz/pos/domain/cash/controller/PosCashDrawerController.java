package com.sisuz.pos.domain.cash.controller;

import com.sisuz.pos.common.api.ApiResponse;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerCreateRequest;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerFilter;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerResponse;
import com.sisuz.pos.domain.cash.controller.dto.PosCashDrawerUpdateRequest;
import com.sisuz.pos.domain.cash.service.PosCashDrawerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cash-drawers")
@RequiredArgsConstructor
public class PosCashDrawerController {

    private final PosCashDrawerService cashDrawerService;

    @PostMapping
    public ApiResponse<PosCashDrawerResponse> create(@RequestBody @Valid PosCashDrawerCreateRequest request) {
        return ApiResponse.success(cashDrawerService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PosCashDrawerResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid PosCashDrawerUpdateRequest request
    ) {
        return ApiResponse.success(cashDrawerService.update(id, request));
    }

    @GetMapping("/{id}")
    public ApiResponse<PosCashDrawerResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(cashDrawerService.getById(id));
    }

    @PatchMapping("/{id}/activate")
    public void activate(@PathVariable Long id) {
        cashDrawerService.activate(id);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        cashDrawerService.deactivate(id);
    }

    @GetMapping
    public ApiResponse<Page<PosCashDrawerResponse>> getAllByTerminalId(
            PosCashDrawerFilter filter,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return ApiResponse.success(cashDrawerService.getAllByTerminalId(filter, pageable));
    }
}
