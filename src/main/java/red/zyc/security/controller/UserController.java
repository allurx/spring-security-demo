package red.zyc.security.controller;

import red.zyc.security.model.ro.UsernamePasswordLoginRo;
import red.zyc.security.security.UsernamePasswordAuthenticationFilter;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author zyc
 */
@Api(tags = "用户")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test1")
    public ResponseEntity<String> test1() {
        return ok("test1");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        return ok("test2");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/test3")
    public ResponseEntity<String> test3() {
        return ok("test3");
    }

    @GetMapping("/test4")
    public ResponseEntity<String> test4() {
        return ok("test4");
    }

    /**
     * 登录请求，仅用来给swagger生成登录请求接口，方便调试。
     * 具体认证是通过{@link UsernamePasswordAuthenticationFilter}实现的
     *
     * @return {@link ResponseEntity}
     */
    @PostMapping("/login")
    public ResponseEntity<UsernamePasswordLoginRo> login(@RequestBody UsernamePasswordLoginRo usernamePasswordLoginRo) {
        return ok(usernamePasswordLoginRo);
    }

}
