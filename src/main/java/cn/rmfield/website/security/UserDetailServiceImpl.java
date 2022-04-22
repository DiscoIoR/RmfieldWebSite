package cn.rmfield.website.security;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户
        RfUser rfUser = userRepository.findByUsername(username);
        if (rfUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        return new LoginUser(rfUser);
    }
}
