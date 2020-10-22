package com.zyc.security.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.zyc.security.common.util.WebUtil.response;

/**
 * 接口需要特定的权限，但是当前用户是匿名用户或者是记住我的用户
 *
 * @author zyc
 * @see ExceptionTranslationFilter#handleSpringSecurityException
 */
public class CustomizedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        response("用户未认证");
    }
}
