package com.zyc.security.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zyc.security.common.util.WebUtil.response;

/**
 * 接口无权访问处理器
 * {@link ExceptionTranslationFilter#handleSpringSecurityException}
 *
 * @author zyc
 */
public class CustomizedAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response("用户未授权");
    }
}
