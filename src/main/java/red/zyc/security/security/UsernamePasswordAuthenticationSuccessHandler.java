package red.zyc.security.security;

import com.google.gson.Gson;
import red.zyc.security.common.constant.StringConstant;
import red.zyc.security.model.User;
import red.zyc.security.service.UserService;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户名密码认证成功处理器
 *
 * @author zyc
 */
@Setter
public class UsernamePasswordAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;
    private static final Gson GSON = new Gson();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        userService.saveSession(user);
        response.setContentType(StringConstant.JSON_CONTENT_TYPE);
        response.setCharacterEncoding(StringConstant.UTF_8);
        response.getWriter().write(GSON.toJson(user));
    }
}
