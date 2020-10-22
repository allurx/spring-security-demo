package com.zyc.security.dao;

import com.zyc.security.model.User;

/**
 * @author zyc
 */
public interface UserMapper {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);
}
