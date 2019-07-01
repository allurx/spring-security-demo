package com.zyc.security.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.Filter;

/**
 * 用户名密码登录配置
 *
 * @param <B> 对{@link HttpSecurityBuilder}进行配置
 * @author zyc
 * @see FormLoginConfigurer
 */
public class UsernamePasswordAuthenticationConfigurer<B extends HttpSecurityBuilder<B>>
        extends AbstractAuthenticationFilterConfigurer<B, UsernamePasswordAuthenticationConfigurer<B>, UsernamePasswordAuthenticationFilter> {

    private AuthenticationSuccessHandler authenticationSuccessHandler = new UsernamePasswordAuthenticationSuccessHandler();

    private AuthenticationFailureHandler authenticationFailureHandler = new UsernamePasswordAuthenticationFailureHandler();

    private static AntPathRequestMatcher loginProcessingRequestMatcher = new AntPathRequestMatcher("/user/login", "POST");

    private Class<? extends Filter> atFilter = org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class;

    public UsernamePasswordAuthenticationConfigurer() {
        super(new UsernamePasswordAuthenticationFilter(loginProcessingRequestMatcher), null);
    }

    @Override
    public void init(B builder) throws Exception {
//        ExpressionUrlAuthorizationConfigurer<?> configurer = http.getConfigurer(ExpressionUrlAuthorizationConfigurer.class);
//        configurer.getRegistry().antMatchers(loginProcessingRequestMatcher.getPattern()).permitAll();
    }

    @Override
    public void configure(B builder) throws Exception {
        UsernamePasswordAuthenticationFilter filter = this.getAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        ((HttpSecurity) builder).addFilterAt(postProcess(filter), atFilter);
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }


}
