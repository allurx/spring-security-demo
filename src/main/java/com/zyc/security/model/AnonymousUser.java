package com.zyc.security.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 匿名用户
 *
 * @author zyc
 */
public class AnonymousUser extends User {

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return Stream.of((GrantedAuthority) () -> "ROLE_ANONYMOUS").collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return "Anonymous";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
