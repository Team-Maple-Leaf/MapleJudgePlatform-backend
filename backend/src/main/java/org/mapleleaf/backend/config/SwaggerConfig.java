package org.mapleleaf.backend.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import com.fasterxml.classmate.TypeResolver;

import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private final TypeResolver typeResolver;

    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("maple leaf API")
                .description("maple leaf API documentation").build();
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.OAS_30)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
                .useDefaultResponseMessages(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.mapleleaf.backend.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(swaggerInfo());
    }

    @Getter @Setter
    @ApiModel
    static class Page {
        @ApiModelProperty(value = "페이지 번호(0..N)")
        private Integer page;

        @ApiModelProperty(value = "페이지 크기", allowableValues="range[0, 100]")
        private Integer size;

        @ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC)")
        private List<String> sort;
    }
}
