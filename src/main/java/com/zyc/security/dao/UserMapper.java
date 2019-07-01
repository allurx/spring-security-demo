package com.zyc.security.dao;

import com.zyc.security.model.User;

/**
 * @author zyc
 */
public interface UserMapper {

    User getUserByUsername(String username);
}
