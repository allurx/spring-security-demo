package com.zyc.security.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zyc
 */
public class SecurityException extends AuthenticationException {

    public SecurityException(String message) {
        super(message);
    }
}
