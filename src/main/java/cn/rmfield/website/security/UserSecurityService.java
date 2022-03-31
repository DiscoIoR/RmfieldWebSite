package cn.rmfield.website.security;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户
        RfUser rfUser = userRepository.findByUsername(username);
        if(rfUser ==null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        //传递用户权限
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Authority> roles = rfUser.getAuthorityList();
        for(Authority authority:roles){
            GrantedAuthority sg=new SimpleGrantedAuthority(authority.getRolename());
            authorities.add(sg);
        }

        return new User(rfUser.getUsername(),rfUser.getPassword(),authorities);
    }
}
