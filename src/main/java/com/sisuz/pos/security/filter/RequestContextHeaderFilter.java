package com.sisuz.pos.security.filter;

import com.sisuz.pos.security.context.RequestContext;
import com.sisuz.pos.security.context.RequestContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestContextHeaderFilter extends OncePerRequestFilter {

    private static final String TENANT_HEADER = "Tenant-Id";
    private static final String COMPANY_HEADER = "Company-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        UUID tenantId = parseUuidHeader(request, response, TENANT_HEADER);
        if (response.isCommitted()) return;

        UUID companyId = parseUuidHeader(request, response, COMPANY_HEADER);
        if (response.isCommitted()) return;

        RequestContextHolder.set(new RequestContext(tenantId, companyId));

        try {
            filterChain.doFilter(request, response);
        } finally {
            RequestContextHolder.clear();
        }
    }

    private UUID parseUuidHeader(HttpServletRequest request, HttpServletResponse response, String headerName)
            throws IOException {
        String value = request.getHeader(headerName);
        if (value == null || value.isBlank()) return null;

        try {
            return UUID.fromString(value.trim());
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid " + headerName + " header");
            return null;
        }
    }
}
