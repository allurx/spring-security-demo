package com.zyc.security.service;

import com.zyc.security.common.constant.enums.RedisKey;
import com.zyc.security.config.RedisConfig;
import com.zyc.security.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redis服务
 *
 * @author zyc
 */
@Service
public class RedisService {

    /**
     * @see RedisConfig#redisJsonTemplate
     */
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(@Qualifier("jsonRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置键值对
     *
     * @param key      键
     * @param value    值
     * @param redisKey 键前缀
     */
    public void set(Object key, Object value, RedisKey redisKey) {
        redisTemplate.opsForValue().set(redisKey.getKey() + key, value);
    }

    /**
     * 获取指定的键对应的值
     *
     * @param key      键
     * @param redisKey 键前缀
     * @param <T>      值类型
     * @return 指定的键对应的值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, RedisKey redisKey) {
        return (T) redisTemplate.opsForValue().get(redisKey.getKey() + key);
    }

    /**
     * 获取用户会话信息
     *
     * @param sessionId 会话id
     * @return 用户会话信息
     */
    public User getSession(Integer sessionId) {
        return get(sessionId, RedisKey.USER);
    }

}
