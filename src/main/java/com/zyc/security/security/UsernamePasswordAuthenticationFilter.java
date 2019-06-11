package com.zyc.security.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用户名密码认证过滤器,替换了spring自带的{@link UsernamePasswordAuthenticationFilter},
 * 该过滤器使用的{@link AuthenticationManager}是spring自带的{@link ProviderManager}
 *
 * @author zyc
 * @see UsernamePasswordAuthenticationFilter
 * @see ProviderManager
 * @see DaoAuthenticationProvider
 */
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/user/login", "POST"));
        setAuthenticationManager(authenticationManager);
        AuthenticationSuccessHandler authenticationSuccessHandler = new UsernamePasswordAuthenticationSuccessHandler();
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        AuthenticationFailureHandler authenticationFailureHandlerHandler = new UsernamePasswordAuthenticationFailureHandler();
        setAuthenticationFailureHandler(authenticationFailureHandlerHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 注意request.getInputStream()只能读取一次
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String username = null, password = null;
        if (StringUtils.hasText(body)) {
            JSONObject jsonObject = JSON.parseObject(body);
            username = jsonObject.getString("nickname");
            password = jsonObject.getString("password");
        }
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                username, password);
        return this.getAuthenticationManager().authenticate(token);
    }

}
