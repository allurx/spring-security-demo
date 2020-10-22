package com.zyc.security.service;

import com.zyc.security.common.constant.StringConstant;
import com.zyc.security.common.constant.enums.RedisKey;
import com.zyc.security.common.util.TokenUtil;
import com.zyc.security.dao.UserMapper;
import com.zyc.security.model.User;
import com.zyc.security.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zyc
 */
@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RedisService redisService;

    public UserService(UserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    @Override
    public User loadUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        TokenUtil.generateToken(user);
        return user;
    }

    /**
     * 根据token获取redis中的用户
     *
     * @param token token
     * @return 用户信息
     */
    public User getUser(String token) {
        log.info("The current request token is: " + token);
        return Optional.ofNullable(token)
                .flatMap(t -> Optional.of(TokenUtil.parseClaim(t, StringConstant.USER_ID, Integer.class)))
                .map(redisService::getSession)
                .orElseThrow(new SecurityException("缺失用户信息"));
    }

    /**
     * 将用户信息保存到redis中
     *
     * @param user 用户信息
     */
    public void saveSession(User user) {
        redisService.set(user.getId(), user, RedisKey.USER);
    }
}
