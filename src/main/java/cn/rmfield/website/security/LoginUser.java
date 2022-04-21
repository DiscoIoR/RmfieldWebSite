package cn.rmfield.website.security;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.RfUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private RfUser rfUser;

    //权限列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //获取权限信息
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        List<Authority> roles = rfUser.getAuthorityList();
        for(Authority authority:roles){
            GrantedAuthority sg=new SimpleGrantedAuthority(authority.getRolename());
            authorityList.add(sg);
        }

        return authorityList;
    }

    //密码
    @Override
    public String getPassword() {
        return rfUser.getPassword();
    }

    //用户名
    @Override
    public String getUsername() {
        return rfUser.getUsername();
    }

    //账户未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //非锁定账户
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //凭证未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
