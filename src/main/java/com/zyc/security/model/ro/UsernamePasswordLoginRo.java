package com.zyc.security.model.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zyc
 */
@Getter
@Setter
@ApiModel("登录信息")
public class UsernamePasswordLoginRo {

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;
}
