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
public final class TokenUtil {

    private TokenUtil() {
    }

    /**
     * 根据用户信息生成token
     *
     * @param user 用户信息
     */
    public static void generateToken(User user) {
        String token = Jwts.builder()
                .setHeaderParam(StringConstant.TYP, StringConstant.JWT)
                .claim(StringConstant.USER_ID, user.getId())
                .claim(StringConstant.MARK, user.getMark())
                .signWith(SignatureAlgorithm.HS512, Security.KEY)
                .compact();
        user.setToken(token);
    }

    /**
     * 从token中解析对应的claim
     *
     * @param token token
     * @param claim claim
     * @param clazz claim值的{@link Class}
     * @param <T>   claim值的类型
     * @return 对应的claim值
     */
    public static <T> T parseClaim(String token, String claim, Class<T> clazz) {
        return Jwts.parser()
                .setSigningKey(Security.KEY)
                .parseClaimsJws(token)
                .getBody()
                .get(claim, clazz);
    }

}
