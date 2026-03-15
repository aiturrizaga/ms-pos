package com.sisuz.pos.domain.session.controller;

import com.sisuz.pos.common.api.ApiResponse;
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
@RequestMapping("/v1/sessions")
@RequiredArgsConstructor
public class PosSessionController {

    private final PosSessionService sessionService;

    @PostMapping("/open")
    public ApiResponse<PosSessionResponse> open(@RequestBody PosSessionOpenRequest request) {
        return ApiResponse.success(sessionService.open(request));
    }

    @PostMapping("/close")
    public ApiResponse<PosSessionResponse> close(@RequestBody PosSessionCloseRequest request) {
        return ApiResponse.success(sessionService.close(request));
    }

    @GetMapping("/current/{terminalId}")
    public ApiResponse<PosSessionResponse> current(@PathVariable Long terminalId) {
        return ApiResponse.success(sessionService.getCurrentSession(terminalId));
    }

    @GetMapping
    public ApiResponse<Page<PosSessionResponse>> getAll(
            PosSessionFilter filter,
            @PageableDefault(size = 20, sort = "openedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ApiResponse.success(sessionService.getAll(filter, pageable));
    }
}
