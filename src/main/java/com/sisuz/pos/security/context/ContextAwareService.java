package com.sisuz.pos.security.context;

import com.sisuz.pos.common.exception.BusinessException;

import java.util.UUID;

public abstract class ContextAwareService {

    protected UUID currentTenantId() {
        RequestContext ctx = RequestContextHolder.get();
        UUID tenantId = (ctx == null) ? null : ctx.tenantId();

        if (tenantId == null) {
            throw new BusinessException(4100, "Tenant-Id header is required");
        }
        return tenantId;
    }

    protected UUID currentCompanyId() {
        RequestContext ctx = RequestContextHolder.get();
        UUID companyId = (ctx == null) ? null : ctx.companyId();

        if (companyId == null) {
            throw new BusinessException(4101, "Company-Id header is required");
        }
        return companyId;
    }

    protected UUID currentTenantIdOrNull() {
        RequestContext ctx = RequestContextHolder.get();
        return ctx == null ? null : ctx.tenantId();
    }

    protected UUID currentCompanyIdOrNull() {
        RequestContext ctx = RequestContextHolder.get();
        return ctx == null ? null : ctx.companyId();
    }
}
