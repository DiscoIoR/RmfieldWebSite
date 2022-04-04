package cn.rmfield.website.service.guide;

import cn.rmfield.website.entity.*;
import cn.rmfield.website.entity.arknights.ArknightsStatistics;
import cn.rmfield.website.repository.arknights.ArknightsStatisticsRepostiory;
import cn.rmfield.website.repository.InvitationCodeRepository;
import cn.rmfield.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuideServiceImpl implements GuideService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvitationCodeRepository invitationCodeRepository;
    @Autowired
    private ArknightsStatisticsRepostiory arknightsStatisticsRepostiory;

    //注册
    @Override
    public String register(RfUser userDomain) {
        //校验用户名唯一性
        RfUser findUserResult = userRepository.findByUsername(userDomain.getUsername());
        if(findUserResult != null){
            return "redirect:/register?userexist";
        }

        //检验邀请码
        InvitationCode invitationCode = invitationCodeRepository.findByCode(userDomain.getInvitationcode());
        if (invitationCode == null) {
            return "redirect:/register?verifyerror";
        } else if (invitationCode.getState() == 0) {
            return "redirect:/register?verifyerror";
        }

        //设置密码
        String secret = new BCryptPasswordEncoder().encode(userDomain.getPassword());
        userDomain.setPassword(secret);

        //设置权限
        List<Authority> authorityList = new ArrayList<Authority>();
//        Authority auth1 = new Authority();
//        auth1.setId(1);
//        auth1.setRolename("ROLE_ADMIN");
//        authorityList.add(auth1);
//        Authority auth2 = new Authority();
//        auth2.setId(2);
//        auth2.setRolename("ROLE_DBA");
//        authorityList.add(auth2);
        Authority auth3 = new Authority();
        auth3.setId(3);
        auth3.setRolename("ROLE_USER");
        authorityList.add(auth3);
        userDomain.setAuthorityList(authorityList);

        //设置方舟统计记录
        ArknightsStatistics arknightsStatistics = new ArknightsStatistics();
        arknightsStatistics.setArknights_nickName("unknow");
        arknightsStatistics.setArknights_uid("unknow");
        arknightsStatistics.setTotalCount(0);
        arknightsStatistics.setSixCount(0);
        arknightsStatistics.setFiveCount(0);
        arknightsStatistics.setFourCount(0);
        arknightsStatistics.setThreeCount(0);
        arknightsStatistics.setSixRate(0D);
        arknightsStatistics.setFiveRate(0D);
        arknightsStatistics.setFourRate(0D);
        arknightsStatistics.setThreeRate(0D);
        arknightsStatistics.setDiamondGrossIncome(0);
        arknightsStatistics.setDiamondGrossExpenses(0);
        arknightsStatistics.setTotalCost(0);
        arknightsStatisticsRepostiory.save(arknightsStatistics);
        userDomain.setArknightsStatistics(arknightsStatistics);

        RfUser mu = userRepository.save(userDomain);
        if (mu != null) {
            invitationCode.setState(0);
            invitationCodeRepository.save(invitationCode);
            return "login";
        } else {
            return "redirect:/register?unknowerror";
        }
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