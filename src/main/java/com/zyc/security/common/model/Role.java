package com.zyc.security.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author zyc
 */
@Getter
@Setter
@ApiModel("角色")
public class Role implements GrantedAuthority {

    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty("角色名称")
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
