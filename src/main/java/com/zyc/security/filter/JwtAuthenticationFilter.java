package com.zyc.security.filter;

import com.zyc.security.common.constant.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt令牌过滤器
 *
 * @author zyc
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

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
