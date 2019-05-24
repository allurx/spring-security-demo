package com.zyc.security.common.util;


import com.zyc.security.common.constant.Security;
import com.zyc.security.common.constant.StringConstant;
import com.zyc.security.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token工具类
 *
 * @author zyc
 */
public class TokenUtil {


    public static String generateToken(User user) {
        String token = Jwts.builder()
                .setHeaderParam(StringConstant.TYP, StringConstant.JWT)
                .claim(StringConstant.USER_ID, user.getId())
                .signWith(SignatureAlgorithm.HS512, Security.KEY).compact();
        user.setToken(token);
        return token;
    }

    private static <T> T parseClaim(String token, String claim, Class<T> clazz) {
        return Jwts.parser()
                .setSigningKey(Security.KEY)
                .parseClaimsJws(token)
                .getBody()
                .get(claim, clazz);
    }

}
