package com.zyc.security.security;

import org.springframework.security.core.AuthenticationException;

import java.util.function.Supplier;

/**
 * @author zyc
 */
public class SecurityException extends AuthenticationException implements Supplier<AuthenticationException> {

    public SecurityException(String message) {
        super(message);
    }

    @Override
    public AuthenticationException get() {
        return this;
    }
}
