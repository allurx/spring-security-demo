package com.zyc.security.config;

import com.zyc.security.common.constant.Security;
import com.zyc.security.filter.*;
import com.zyc.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyc
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private StaticHeadersWriter staticHeadersWriter;

    // 跨域支持
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("Access-Control-Allow-Origin", "*"));
        headers.add(new Header("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACES"));
        headers.add(new Header("Access-Control-Allow-Headers", Security.TOKEN));
        staticHeadersWriter = new StaticHeadersWriter(headers);
    }

    /**
     * http安全配置
     *
     * @param http http安全对象
     * @throws Exception http安全异常信息
     */
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
                .and()
                // 添加自定义的过滤器
                .addFilterAt(new UsernamePasswordAuthenticationFilter(authenticationManager()), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new OptionsRequestFilter(), HeaderWriterFilter.class)
                // 配置认证失败和拒绝访问处理器
                .exceptionHandling()
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .and()
                // 响应头设置
                .headers()
                .addHeaderWriter(staticHeadersWriter)
                .and()
                // 不通过HttpSession来获取SecurityContext
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 禁用csrf过滤器
                .csrf()
                .disable()
                // 禁用登出过滤器
                .logout()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(new JwtAuthenticationProvider())
                // 配置了DaoAuthenticationProvider
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
        ;
    }

}
