package com.zyc.security.controller;

import com.zyc.security.model.ro.UsernamePasswordLoginRo;
import com.zyc.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author zyc
 */
@Api(tags = "用户")
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
    public ResponseEntity<UsernamePasswordLoginRo> login(@RequestBody UsernamePasswordLoginRo usernamePasswordLoginRo) {
        return ResponseEntity.ok(usernamePasswordLoginRo);
    }

}
