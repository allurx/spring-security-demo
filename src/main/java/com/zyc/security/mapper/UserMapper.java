package com.zyc.security.mapper;

import com.zyc.security.model.User;

/**
 * @author zyc
 */
public interface UserMapper {

    User getUserByNickName(String nickName);
}
