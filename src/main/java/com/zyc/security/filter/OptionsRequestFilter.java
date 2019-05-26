package com.zyc.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 复杂跨域会先发送一次Options请求来判断服务端是否允许跨域
 *
 * @author zyc
 */
@Slf4j
public class OptionsRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = "OPTIONS";
        // OPTIONS请求直接放过
        if (method.equals(request.getMethod())) {
            return;
        }
        filterChain.doFilter(request, response);
    }
}
