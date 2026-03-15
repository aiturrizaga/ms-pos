package com.sisuz.pos.domain.cash.controller;

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
    public PosCashDrawerResponse create(
            @RequestBody @Valid PosCashDrawerCreateRequest request
    ) {
        return cashDrawerService.create(request);
    }

    @PutMapping("/{id}")
    public PosCashDrawerResponse update(
            @PathVariable Long id,
            @RequestBody @Valid PosCashDrawerUpdateRequest request
    ) {
        return cashDrawerService.update(id, request);
    }

    @GetMapping("/{id}")
    public PosCashDrawerResponse getById(@PathVariable Long id) {
        return cashDrawerService.getById(id);
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
    public Page<PosCashDrawerResponse> getAllByTerminalId(
            PosCashDrawerFilter filter,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return cashDrawerService.getAllByTerminalId(filter, pageable);
    }
}
