package red.zyc.security.config;

import red.zyc.security.model.AnonymousUser;
import red.zyc.security.security.CustomizedAccessDeniedHandler;
import red.zyc.security.security.CustomizedAuthenticationEntryPoint;
import red.zyc.security.security.JwtAuthenticationConfigurer;
import red.zyc.security.security.UsernamePasswordAuthenticationConfigurer;
import red.zyc.security.service.UserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

    /**
     * 匿名用户
     */
    private final AnonymousUser anonymousUser = new AnonymousUser();

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 整个spring-security需要忽略的请求,作者建议这些请求一般是一些静态资源
     */
    private final String[] ignoreUrls = {"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs"};


    public WebSecurityConfig(UserService userService) {
        // 禁用默认的配置,对框架不熟悉最好不要设置这个值
        super(true);
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 添加用户名密码认证配置者
                .apply(new UsernamePasswordAuthenticationConfigurer<>())
                .and()

                // 添加jwt认证配置者
                .apply(new JwtAuthenticationConfigurer<>())
                .and()

                // 添加匿名认证配置者
                .anonymous().principal(anonymousUser).authorities(anonymousUser.getAuthorities())
                .and()

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
