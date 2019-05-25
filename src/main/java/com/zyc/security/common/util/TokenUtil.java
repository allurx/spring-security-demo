package com.zyc.security.common.util;


import com.zyc.security.common.constant.Security;
import com.zyc.security.common.constant.StringConstant;
import com.zyc.security.common.constant.enums.RedisKey;
import com.zyc.security.model.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

/**
 * token工具类
 *
 * @author zyc
 */
public class TokenUtil {

    private static final RedisUtil REDIS_UTIL = SpringUtil.getBean(RedisUtil.class);

    public static void generateToken(User user) {
        String token = Jwts.builder()
                .setHeaderParam(StringConstant.TYP, StringConstant.JWT)
                .claim(StringConstant.USER_ID, user.getId())
                .claim(StringConstant.MARK, user.getMark())
                .signWith(SignatureAlgorithm.HS512, Security.KEY)
                .compact();
        user.setToken(token);
        REDIS_UTIL.set(user.getId(), user, RedisKey.USER);
    }

    public static User getUser(String token) {
        if (StringUtils.isBlank(token)) {
            throw new JwtException("token为空");
        }
        Integer userId = parseClaim(token, StringConstant.USER_ID, Integer.class);
        User user = REDIS_UTIL.get(userId, RedisKey.USER, User.class);
        if (user == null) {
            throw new JwtException("缺失用户信息");
        }
        return user;
    }

    public static <T> T parseClaim(String token, String claim, Class<T> clazz) {
        return Jwts.parser()
                .setSigningKey(Security.KEY)
                .parseClaimsJws(token)
                .getBody()
                .get(claim, clazz);
    }
}
