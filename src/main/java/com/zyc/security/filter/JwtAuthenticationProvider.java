package com.zyc.security.filter;

import com.zyc.security.common.constant.StringConstant;
import com.zyc.security.common.util.TokenUtil;
import com.zyc.security.model.User;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author zyc
 */
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user;
        try {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            String jwt = String.valueOf(jwtAuthenticationToken.getPrincipal());
            user = TokenUtil.getUser(jwt);
            String mark = TokenUtil.parseClaim(jwt, StringConstant.MARK, String.class);
            if (!user.getMark().equals(mark)) {
                throw new JwtException("账号已在其它地方登陆");
            }
        } catch (Throwable e) {
            log.warn(e.getMessage());
            user = null;
        }
        return new JwtAuthenticationToken(user, "", user == null ? null : user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}
