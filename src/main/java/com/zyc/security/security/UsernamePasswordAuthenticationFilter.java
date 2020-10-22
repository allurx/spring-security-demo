package com.zyc.security.security;

import com.google.gson.Gson;
import com.zyc.security.model.ro.UsernamePasswordLoginRo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 用户名密码认证过滤器,替换了spring自带的{@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter},
 * 该过滤器使用的{@link AuthenticationManager}是spring自带的{@link ProviderManager}
 *
 * @author zyc
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 * @see ProviderManager
 * @see DaoAuthenticationProvider
 */
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Gson GSON = new Gson();

    public UsernamePasswordAuthenticationFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 注意request.getInputStream()只能读取一次
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        UsernamePasswordLoginRo usernamePasswordLoginRo = Optional.ofNullable(GSON.fromJson(body, UsernamePasswordLoginRo.class)).orElse(new UsernamePasswordLoginRo());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usernamePasswordLoginRo.getUsername(), usernamePasswordLoginRo.getPassword());
        token.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }

}
