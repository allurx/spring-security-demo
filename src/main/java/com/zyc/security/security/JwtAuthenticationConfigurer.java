package com.zyc.security.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import javax.servlet.Filter;

/**
 * jwt认证配置者
 *
 * @author zyc
 */
public class JwtAuthenticationConfigurer<B extends HttpSecurityBuilder<B>>
        extends AbstractHttpConfigurer<JwtAuthenticationConfigurer<B>, B> {

    private Class<? extends Filter> beforeFilter = UsernamePasswordAuthenticationFilter.class;

    @Override
    public void init(B builder) {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
        builder.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    public void configure(B builder) {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        ((HttpSecurity) builder).addFilterBefore(postProcess(filter), beforeFilter);
    }

}
