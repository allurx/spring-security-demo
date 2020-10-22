package com.zyc.security.service;

import com.zyc.security.common.constant.enums.RedisKey;
import com.zyc.security.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具类
 *
 * @author zyc
 */
@Component
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(@Qualifier("jsonRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(Object key, Object value, RedisKey redisKey) {
        redisTemplate.opsForValue().set(redisKey.getKey() + key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key, RedisKey redisKey) {
        return (T) redisTemplate.opsForValue().get(redisKey.getKey() + key);
    }

    public User getUser(Integer userId) {
        return get(userId, RedisKey.USER);
    }


}
