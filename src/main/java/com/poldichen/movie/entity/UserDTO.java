package com.poldichen.movie.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author poldi.chen
 * @className UserDTO
 * @description TODO
 * @date 2019/8/20 12:34
 **/
@Data
public class UserDTO implements UserDetails {

    /**
     * 是否记住密码
     */
    private Boolean remember;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;


    /**
     * 获取权限信息
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        List<GrantedAuthority> grantedAuthorities =
                roles.stream().map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toList());
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * 账户是否未过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账户凭证是否未过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
