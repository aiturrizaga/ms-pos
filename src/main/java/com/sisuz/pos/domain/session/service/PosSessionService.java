package com.sisuz.pos.domain.session.service;

import com.sisuz.pos.domain.session.controller.dto.PosSessionCloseRequest;
import com.sisuz.pos.domain.session.controller.dto.PosSessionFilter;
import com.sisuz.pos.domain.session.controller.dto.PosSessionOpenRequest;
import com.sisuz.pos.domain.session.controller.dto.PosSessionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PosSessionService {

    PosSessionResponse open(PosSessionOpenRequest request);

    PosSessionResponse close(PosSessionCloseRequest request);

    PosSessionResponse getCurrentSession(Long terminalId);

    Page<PosSessionResponse> getAll(PosSessionFilter filter, Pageable pageable);

}
