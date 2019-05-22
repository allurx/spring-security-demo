package com.zyc.security.config;

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

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 不需要认证请求
     */
    private String[] permitAllUrl = {"/user/loginPage", "/user/login", "/error", "/favicon.ico"};

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
                // 其它任何请求都需要认证
                .anyRequest()
                .authenticated()
                .and()
                // 设置表单登录请求为/user/login并且设置登入用户名和密码字段名称（默认是username和password）
                .formLogin()
                .loginPage("/user/loginPage")
                .loginProcessingUrl("/user/login")
                .usernameParameter("userName")
                .passwordParameter("password")
                .and()
                // 禁用csrf，否则会报错Invalid csrf token
                .csrf()
                .disable()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 在内存中生成两个用户以及拥有的角色
                .inMemoryAuthentication()
                .withUser("user1")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .and()
                .withUser("user2")
                .password(passwordEncoder.encode("123456"))
                .roles("USER", "ADMIN")
                .and()
                // 配置密码编码器（spring security5需要配置）
                .passwordEncoder(passwordEncoder);
    }

}
