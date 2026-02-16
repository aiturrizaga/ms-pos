package com.sisuz.pos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Organization API")
                        .description("Organization REST API services")
                        .license(new License().name("SISUZ").url("https://sisuz.com"))
                        .version("1.0.0")
                );
    }

    @Bean
    public OpenApiCustomizer tenantAndCompanyHeadersCustomizer() {
        return openApi -> {
            if (openApi.getPaths() == null) return;

            openApi.getPaths().forEach((path, pathItem) -> {

                boolean needsTenant =
                        path.startsWith("/v1/tenant")
                                || path.startsWith("/v1/partner");

                boolean needsCompany =
                        path.startsWith("/v1/store")
                                || path.startsWith("/v1/partner");

                if (!needsTenant && !needsCompany) return;

                pathItem.readOperations().forEach(operation -> {

                    if (needsTenant) {
                        addHeaderIfMissing(
                                operation,
                                "Tenant-Id",
                                "Tenant context id",
                                true
                        );
                    }

                    if (needsCompany) {
                        addHeaderIfMissing(
                                operation,
                                "Company-Id",
                                "Company context id",
                                true
                        );
                    }
                });
            });
        };
    }

    private void addHeaderIfMissing(
            Operation operation,
            String name,
            String description,
            boolean required
    ) {
        boolean exists = operation.getParameters() != null
                && operation.getParameters().stream().anyMatch(p ->
                name.equalsIgnoreCase(p.getName())
                        && "header".equalsIgnoreCase(p.getIn())
        );

        if (!exists) {
            Parameter header = new Parameter()
                    .in("header")
                    .name(name)
                    .required(required)
                    .description(description)
                    .schema(new StringSchema().format("uuid"));

            operation.addParametersItem(header);
        }
    }
}
