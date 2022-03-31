package cn.rmfield.website.service;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.InvitationCode;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.InvitationCodeRepository;
import cn.rmfield.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvitationCodeRepository invitationCodeRepository;

    //注册
    @Override
    public String register(RfUser userDomain) {
        //检验邀请码
        InvitationCode invitationCode = invitationCodeRepository.findByCode(userDomain.getInvitationcode());
        if (invitationCode == null) {
            return "redirect:/toRegister?failed";
        } else if (invitationCode.getState() == 0) {
            return "redirect:/toRegister?failed";
        }

        //设置权限
        List<Authority> authorityList = new ArrayList<Authority>();
        Authority auth = new Authority();
        Authority auth1 = new Authority();
        Authority auth2 = new Authority();
        /*管理员权限
        auth1.setId(1);
        auth1.setRolename("ROLE_ADMIN");
        auth2.setId(2);
        auth2.setRolename("ROLE_DBA");
        authorityList.add(auth1);
        authorityList.add(auth2);*/
        auth.setId(3);
        auth.setRolename("ROLE_USER");
        authorityList.add(auth);
        userDomain.setAuthorityList(authorityList);

        //加密密码
        String secret = new BCryptPasswordEncoder().encode(userDomain.getPassword());
        userDomain.setPassword(secret);

        RfUser mu = userRepository.save(userDomain);
        if (mu != null) {
            invitationCode.setState(0);
            invitationCodeRepository.save(invitationCode);
            return "/login";
        } else {
            return "redirect:/toRegister?failed";
        }
    }

    //用户登录成功
    @Override
    public String loginSuccess(Model model) {
        model.addAttribute("user", getUname());
        model.addAttribute("role", getAuthorities());
        return "/user/loginSuccess";
    }

    //管理员登录成功
    @Override
    public String main(Model model) {
        model.addAttribute("user", getUname());
        model.addAttribute("role", getAuthorities());
        return "/admin/main";
    }

    //权限不足，拒绝访问
    @Override
    public String deniedAccess(Model model) {
        model.addAttribute("user", getUname());
        model.addAttribute("role", getAuthorities());
        return "/deniedAccess";
    }

    //注销登录
    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            //注销
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    //获取当前用户名称
    private String getUname() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //获取当前用户权限
    private String getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            roles.add(ga.getAuthority());
        }
        return roles.toString();
    }
}
