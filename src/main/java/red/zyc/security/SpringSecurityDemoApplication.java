package red.zyc.security;

import red.zyc.security.common.constant.Security;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zyc
 */
@MapperScan(Security.MAPPER_PACKAGE)
@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

}
