package com.zyc.security.security;

import com.zyc.security.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import javax.servlet.Filter;

/**
 * jwt认证配置者
 *
 * @author zyc
 */
public class JwtAuthenticationConfigurer<B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<JwtAuthenticationConfigurer<B>, B> {

    private static final Class<? extends Filter> BEFORE_FILTER = UsernamePasswordAuthenticationFilter.class;
    private final JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
    private final JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();

    @Override
    public void configure(B builder) {
        UserService userService = builder.getSharedObject(UserService.class);
        jwtAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        jwtAuthenticationProvider.setUserService(userService);
        builder.authenticationProvider(jwtAuthenticationProvider);
        builder.addFilterBefore(postProcess(jwtAuthenticationFilter), BEFORE_FILTER);
    }

}
