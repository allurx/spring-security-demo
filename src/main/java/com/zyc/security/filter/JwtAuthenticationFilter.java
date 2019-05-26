package com.zyc.security.filter;

import com.zyc.security.common.constant.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt令牌过滤器,该过滤器没有像{@link UsernamePasswordAuthenticationFilter}那样继承
 * {@link AbstractAuthenticationProcessingFilter}的原因是该过滤器的初衷是为了解析Jwt token的,
 * 也就是认为经过该过滤器的请求被认为是已经登录认证过的合法请求，而不是为了去认证，仅仅是为了
 * 解析token将认证信息保存到{@link SecurityContext}中，为后续抵达controller层的鉴权做铺垫
 *
 * @author zyc
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 只匹配带有Authentication请求头的请求
     */
    private RequestMatcher requestMatcher = new RequestHeaderRequestMatcher(Security.TOKEN);

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 是否匹配请求
        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = request.getHeader(Security.TOKEN);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt, "");
        Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
        log.debug("Updating SecurityContextHolder to contain：" + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }
}
