package com.zyc.security.common.util;

import com.zyc.security.common.constant.enums.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author zyc
 */
@Component
public class RedisUtil {

    @Qualifier("jsonRedisTemplate")
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(Object key, Object value, RedisKey redisKey) {
        redisTemplate.opsForValue().set(redisKey.getKey() + key, value);
    }

    public Object get(String key, RedisKey redisKey) {
        return redisTemplate.opsForValue().get(redisKey.getKey() + key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key, RedisKey redisKey, Class<T> clazz) {
        return (T) redisTemplate.opsForValue().get(redisKey.getKey() + key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public void setForTimeSecond(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void setForTimeSecond(String key, Object value, long time, RedisKey redisKey) {
        redisTemplate.opsForValue().set(redisKey.getKey() + key, value, time, TimeUnit.SECONDS);
    }

}
