package red.zyc.security.common.constant.enums;

import lombok.Getter;

/**
 * @author zyc
 */
@Getter
public enum RedisKey {

    /**
     * 用户
     */
    USER("user:");

    String key;

    RedisKey(String key) {
        this.key = key;
    }
}
