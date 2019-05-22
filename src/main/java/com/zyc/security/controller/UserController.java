package com.zyc.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zyc
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @GetMapping("/user1")
    public Authentication getUser1(Authentication authentication) {
        return authentication;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @GetMapping("/user2")
    public Authentication getUser2() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 登录请求
     *
     * @return
     */
    @PostMapping("/login")
    public String login() {
        return "666";
    }

    @PostMapping("/logout")
    public String logout() {
        return "666";
    }


    /**
     * @return 登录页面
     */
    @RequestMapping("/loginPage")
    public String loginPage() {
        return "login";
    }
}
