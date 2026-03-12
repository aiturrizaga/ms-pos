package com.sisuz.pos.domain.session.repository.spec;

import com.sisuz.pos.domain.session.entity.PosSession;
import org.springframework.data.jpa.domain.Specification;

public final class PosSessionSpecs {

    private PosSessionSpecs() {
    }

    public static Specification<PosSession> withFilters(PosSessionSpecFilter filter) {

        return Specification.allOf(
                terminalEq(filter.getTerminalId()),
                drawerEq(filter.getDrawerId()),
                statusEq(filter.getStatus()),
                openedByLike(filter.getOpenedBy()),
                openedBetween(filter.getOpenedFrom(), filter.getOpenedTo())
        );
    }

    public static Specification<PosSession> terminalEq(Long terminalId) {
        return (root, query, cb) ->
                terminalId == null ? cb.conjunction() :
                        cb.equal(root.get("terminal").get("id"), terminalId);
    }

    public static Specification<PosSession> drawerEq(Long drawerId) {
        return (root, query, cb) ->
                drawerId == null ? cb.conjunction() :
                        cb.equal(root.get("drawer").get("id"), drawerId);
    }

    public static Specification<PosSession> statusEq(String status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() :
                        cb.equal(root.get("status"), status);
    }

    public static Specification<PosSession> openedByLike(String value) {
        return (root, query, cb) -> {

            if (value == null || value.isBlank()) return cb.conjunction();

            String pattern = "%" + value.toLowerCase() + "%";

            return cb.like(cb.lower(root.get("openedBy")), pattern);
        };
    }

    public static Specification<PosSession> openedBetween(
            java.time.Instant from,
            java.time.Instant to
    ) {

        return (root, query, cb) -> {

            if (from == null && to == null) return cb.conjunction();

            if (from != null && to != null)
                return cb.between(root.get("openedAt"), from, to);

            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("openedAt"), from);

            return cb.lessThanOrEqualTo(root.get("openedAt"), to);
        };
    }
}
