package com.sisuz.pos.domain.session.controller;

import com.sisuz.pos.domain.session.controller.dto.PosSessionCloseRequest;
import com.sisuz.pos.domain.session.controller.dto.PosSessionFilter;
import com.sisuz.pos.domain.session.controller.dto.PosSessionOpenRequest;
import com.sisuz.pos.domain.session.controller.dto.PosSessionResponse;
import com.sisuz.pos.domain.session.service.PosSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pos-sessions")
@RequiredArgsConstructor
public class PosSessionController {

    private final PosSessionService sessionService;

    @PostMapping("/open")
    public PosSessionResponse open(@RequestBody PosSessionOpenRequest request) {
        return sessionService.open(request);
    }

    @PostMapping("/close")
    public PosSessionResponse close(@RequestBody PosSessionCloseRequest request) {
        return sessionService.close(request);
    }

    @GetMapping("/current/{terminalId}")
    public PosSessionResponse current(@PathVariable Long terminalId) {
        return sessionService.getCurrentSession(terminalId);
    }

    @GetMapping
    public Page<PosSessionResponse> getAll(
            PosSessionFilter filter,
            @PageableDefault(size = 20, sort = "openedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return sessionService.getAll(filter, pageable);
    }
}
