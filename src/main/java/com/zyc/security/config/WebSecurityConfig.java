package com.zyc.security.config;

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
    private String[] permitAllUrl = {"/user/loginPage", "/favicon.ico"};

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
                .formLogin()
                .and()
                // 表单登录配置
                .formLogin()
                // 获取登录页面请求
                .loginPage("/user/loginPage")
                // 处理登录请求的url（如果不设置UsernamePasswordAuthenticationFilter默认的处理url就是loginPage）
                .loginProcessingUrl("/user/login")
                // 认证成功后的重定向请求
                .successForwardUrl("/index")
                // 修改登录名字段名称
                .usernameParameter("userName")
                // 修改登录密码字段
                .passwordParameter("password")
                .and()
                // 登出配置
                .logout()
                // 处理登出请求的url
                .logoutUrl("/user/logout")
                // 登出成功后重定向的url
                .logoutSuccessUrl("/user/login")
                // 禁用csrf，否则会报错Invalid csrf token
                .and()
                .csrf()
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
