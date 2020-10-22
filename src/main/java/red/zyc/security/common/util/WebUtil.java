package red.zyc.security.common.util;

import red.zyc.security.common.constant.StringConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author zyc
 */
@Slf4j
public final class WebUtil {

    private WebUtil() {
    }

    /**
     * @return 绑定到当前线程的HttpServletResponse
     */
    private static HttpServletResponse getHttpServletResponse() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getResponse)
                .orElseThrow(() -> new RuntimeException("HttpServletResponse must not be null"));
    }

    /**
     * 写响应信息
     *
     * @param message 响应信息
     */
    public static void response(String message) {
        Optional.of(getHttpServletResponse())
                .ifPresent(response -> {
                    try {
                        response.setCharacterEncoding(StringConstant.UTF_8);
                        response.getWriter().write(message);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });
    }

}
