package com.zyc.security.controller;

import com.zyc.security.service.UserService;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zyc
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @GetMapping("/getUserByNickName")
    public ResponseEntity<UserDetails> getUserByNickName(@ApiParam(value = "用户昵称", required = true) @RequestParam String nickName) {
        return ResponseEntity.ok(userService.loadUserByUsername(nickName));
    }


    /**
     * 登录请求
     *
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Authentication> login(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

}
