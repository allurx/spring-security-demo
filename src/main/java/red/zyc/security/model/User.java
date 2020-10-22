package red.zyc.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author zyc
 */
@Getter
@Setter
@ApiModel("用户")
public class User implements UserDetails {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @JsonIgnore
    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("令牌")
    private String token;

    @ApiModelProperty("用户登录的唯一标记，用来防止重复登录")
    private String mark = UUID.randomUUID().toString();

    @ApiModelProperty("用户角色")
    private List<Role> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
