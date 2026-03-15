package com.sisuz.pos.domain.cash.repository.spec;

import com.sisuz.pos.domain.cash.entity.PosCashDrawer;
import org.springframework.data.jpa.domain.Specification;

public final class PosCashDrawerSpecs {

    private PosCashDrawerSpecs() {
    }

    public static Specification<PosCashDrawer> withFilters(PosCashDrawerSpecFilter filter) {
        return Specification.allOf(
                terminalIdEq(filter.getTerminalId()),
                activeEq(filter.getActive()),
                qLike(filter.getQ()),
                codeEq(filter.getCode()),
                nameLike(filter.getName())
        );
    }

    public static Specification<PosCashDrawer> terminalIdEq(Long terminalId) {
        return (root, query, cb) ->
                terminalId == null ? cb.conjunction() : cb.equal(root.get("terminal").get("id"), terminalId);
    }

    public static Specification<PosCashDrawer> activeEq(Boolean active) {
        return (root, query, cb) ->
                active == null ? cb.conjunction() : cb.equal(root.get("active"), active);
    }

    public static Specification<PosCashDrawer> codeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("code")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosCashDrawer> nameLike(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            String pattern = "%" + value.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), pattern);
        };
    }

    public static Specification<PosCashDrawer> qLike(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();

            String pattern = "%" + value.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("code")), pattern),
                    cb.like(cb.lower(root.get("name")), pattern)
            );
        };
    }

    public static Specification<PosCashDrawer> idEq(Long id) {
        return (root, query, cb) ->
                id == null ? cb.conjunction() : cb.equal(root.get("id"), id);
    }
}
