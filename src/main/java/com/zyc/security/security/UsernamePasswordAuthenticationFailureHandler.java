package com.zyc.security.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.zyc.security.common.util.WebUtil.response;

/**
 * 用户名密码认证失败处理器
 *
 * @author zyc
 */
public class UsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // org.springframework.security.authentication.dao.DaoAuthenticationProvider.retrieveUser
        if (exception instanceof InternalAuthenticationServiceException) {
            response("找不到该用户");
        }
        // 默认是不会抛出UsernameNotFoundException的，org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.hideUserNotFoundExceptions=true
        if (exception instanceof BadCredentialsException) {
            response("用户名或密码错误");
        }
        // org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.DefaultPreAuthenticationChecks.check
        if (exception instanceof LockedException) {
            response("用户账号已锁定");
        }
        // org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.DefaultPreAuthenticationChecks.check
        if (exception instanceof DisabledException) {
            response("用户账号已禁用");
        }
        // org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.DefaultPreAuthenticationChecks.check
        if (exception instanceof AccountExpiredException) {
            response("用户账号已过期");
        }
        // org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider.DefaultPostAuthenticationChecks.check
        if (exception instanceof CredentialsExpiredException) {
            response("用户账号证书已过期");
        }
    }
}
