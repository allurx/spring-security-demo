package com.zyc.security.security;

import com.zyc.security.common.constant.StringConstant;
import com.zyc.security.common.util.TokenUtil;
import com.zyc.security.model.User;
import com.zyc.security.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author zyc
 */
@Slf4j
@Setter
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwt = String.valueOf(jwtAuthenticationToken.getPrincipal());
        User user = userService.getUser(jwt);
        String mark = TokenUtil.parseClaim(jwt, StringConstant.MARK, String.class);
        if (!user.getMark().equals(mark)) {
            throw new SecurityException("账号已在其它地方登陆");
        }
        return new JwtAuthenticationToken(user, "", user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
