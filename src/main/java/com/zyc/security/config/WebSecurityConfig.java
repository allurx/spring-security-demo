package com.zyc.security.config;

import com.zyc.security.filter.UsernamePasswordAuthenticationFilter;
import com.zyc.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 不需要认证请求
     */
    private String[] permitAllUrl = {"/swagger-ui.html", "/webjars/**","/swagger-resources/**"};

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
                .antMatchers(permitAllUrl)
                .permitAll()
                // 任何请求都需要认证
                .anyRequest()
                .authenticated()
                .and()
                // 添加用户名密码认证过滤器
                .addFilterAt(new UsernamePasswordAuthenticationFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                // 禁用session
                .sessionManagement()
                .disable()
                // 禁用csrf
                .csrf()
                .disable()
                // 禁用登出过滤器
                .logout()
                .disable()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

}
