package com.zyc.security.filter;

import com.zyc.security.common.constant.StringConstant;
import com.zyc.security.common.util.TokenUtil;
import com.zyc.security.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户名密码认证成功处理器
 *
 * @author zyc
 */
public class UsernamePasswordAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        TokenUtil.generateToken(user);
        response.setContentType(StringConstant.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(StringConstant.UTF_8);
        response.getWriter().write(user.toString());

    }
}
