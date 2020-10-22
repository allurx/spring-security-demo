package com.zyc.security.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.zyc.security.common.util.WebUtil.response;

/**
 * 接口无权访问处理器
 *
 * @author zyc
 * @see ExceptionTranslationFilter#handleSpringSecurityException
 */
public class CustomizedAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        response("用户未授权");
    }
}
