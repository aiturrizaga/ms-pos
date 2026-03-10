package com.sisuz.pos.domain.terminal.controller;

import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalCreateRequest;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalFilter;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalResponse;
import com.sisuz.pos.domain.terminal.controller.dto.PosTerminalUpdateRequest;
import com.sisuz.pos.domain.terminal.service.PosTerminalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/terminals")
@RequiredArgsConstructor
public class PosTerminalController {

    private final PosTerminalService service;

    @PostMapping
    public PosTerminalResponse create(@RequestBody @Valid PosTerminalCreateRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public PosTerminalResponse update(@PathVariable Long id, @RequestBody @Valid PosTerminalUpdateRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/activate")
    public void activate(@PathVariable Long id) {
        service.activate(id);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }

    @GetMapping
    public Page<PosTerminalResponse> getAll(
            PosTerminalFilter filter,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return service.getAll(filter, pageable);
    }
}
