package com.zyc.security.config;

import com.zyc.security.security.CustomizedAccessDeniedHandler;
import com.zyc.security.security.CustomizedAuthenticationEntryPoint;
import com.zyc.security.security.JwtAuthenticationConfigurer;
import com.zyc.security.security.UsernamePasswordAuthenticationConfigurer;
import com.zyc.security.service.UserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zyc
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public WebSecurityConfig(UserService userService) {
        // 禁用默认的配置
        super(true);
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 不需要认证的请求
                .antMatchers("/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/api-docs")
                .permitAll()
                // 其它任何请求都需要认证
                .anyRequest()
                .authenticated()
                // 添加用户名密码认证配置者
                .and()
                .apply(new UsernamePasswordAuthenticationConfigurer<>())
                .and()
                // 添加jwt认证配置者
                .apply(new JwtAuthenticationConfigurer<>())
                .and()
                // 配置匿名过滤器
                .anonymous()
                .and()
                // 配置认证失败和拒绝访问处理器
                .exceptionHandling()
                .authenticationEntryPoint(new CustomizedAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomizedAccessDeniedHandler())
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 配置了DaoAuthenticationProvider
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
        ;
    }

}
