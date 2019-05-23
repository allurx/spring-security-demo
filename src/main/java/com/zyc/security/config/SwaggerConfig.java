package com.xunqu.core.config;


import com.zyc.security.common.constant.Security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

/**
 * swagger配置
 *
 * @author zyc
 */
@Configuration
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(protocols())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zyc.security"))
                .build();
    }


    /**
     * @return api文档的详细信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring-security-demo API")
                .version("1.0")
                .description("spring-security-demo接口文档")
                .build();
    }

    /**
     * @return 支持的协议
     */
    private Set<String> protocols() {
        Set<String> protocols = new HashSet<>();
        protocols.add("http");
        return protocols;
    }

    /**
     * @return 接口安全密钥
     */
    private List<ApiKey> securitySchemes() {
        return newArrayList(new ApiKey(Security.TOKEN, Security.TOKEN, "header"));
    }

    /**
     * @return 接口安全上下文
     */
    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex(Security.SWAGGER_PATH_REGEX))
                        .build()
        );
    }

    /**
     * @return 接口安全范围
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
        return newArrayList(new SecurityReference(Security.TOKEN, authorizationScopes));
    }
}
