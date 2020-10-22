package com.zyc.security.security;

import com.zyc.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.Filter;

/**
 * 用户名密码登录配置
 *
 * @param <B> 配置{@link HttpSecurityBuilder}
 * @author zyc
 * @see FormLoginConfigurer
 */
@Slf4j
public class UsernamePasswordAuthenticationConfigurer<B extends HttpSecurityBuilder<B>>
        extends AbstractAuthenticationFilterConfigurer<B, UsernamePasswordAuthenticationConfigurer<B>, UsernamePasswordAuthenticationFilter> {

    private static final RequestMatcher LOGIN_PROCESSING_REQUEST_MATCHER = new AntPathRequestMatcher("/user/login", "POST");
    private static final Class<? extends Filter> AT_FILTER = org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class;
    private final UsernamePasswordAuthenticationSuccessHandler authenticationSuccessHandler = new UsernamePasswordAuthenticationSuccessHandler();
    private final UsernamePasswordAuthenticationFailureHandler authenticationFailureHandler = new UsernamePasswordAuthenticationFailureHandler();
    private final UsernamePasswordAuthenticationFilter authenticationFilter = this.getAuthenticationFilter();

    public UsernamePasswordAuthenticationConfigurer() {
        super(new UsernamePasswordAuthenticationFilter(LOGIN_PROCESSING_REQUEST_MATCHER), null);
    }

    @Override
    public void init(B builder) {
        log.info("UsernamePasswordAuthenticationConfigurer is configuring");
    }

    @Override
    public void configure(B builder) {
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        authenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        authenticationSuccessHandler.setUserService(builder.getSharedObject(UserService.class));
        ((HttpSecurity) builder).addFilterAt(postProcess(authenticationFilter), AT_FILTER);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

}
