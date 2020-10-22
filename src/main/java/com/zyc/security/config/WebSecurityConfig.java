package com.zyc.security.config;

import com.zyc.security.security.CustomizedAccessDeniedHandler;
import com.zyc.security.security.CustomizedAuthenticationEntryPoint;
import com.zyc.security.security.JwtAuthenticationConfigurer;
import com.zyc.security.security.UsernamePasswordAuthenticationConfigurer;
import com.zyc.security.service.UserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

/**
 * @author zyc
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    /**
     * 整个spring-security需要忽略的请求,作者建议这些请求一般是一些静态资源
     */
    private final String[] ignoreUrls = {"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs"};
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public WebSecurityConfig(UserService userService) {
        // 禁用默认的配置,对框架不熟悉最好不要设置这个值
        super(true);
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    // 添加异步认证管理者集成过滤器
                .addFilter(new WebAsyncManagerIntegrationFilter())
                // 添加用户名密码认证配置者
                .apply(new UsernamePasswordAuthenticationConfigurer<>()).and()
                // 添加jwt认证配置者
                .apply(new JwtAuthenticationConfigurer<>()).and()
                // 添加匿名认证配置者
                .anonymous().and()
                // 配置认证失败和拒绝访问处理器
                .exceptionHandling()
                .authenticationEntryPoint(new CustomizedAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomizedAccessDeniedHandler());

        // 设置一些共享的对象
        http.setSharedObject(UserService.class, userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 配置了DaoAuthenticationProvider
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // 忽略的请求
                .antMatchers(ignoreUrls).and()
                // 启用debug
                .debug(false)
        ;
    }

}
