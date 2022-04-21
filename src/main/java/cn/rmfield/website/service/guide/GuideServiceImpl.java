package cn.rmfield.website.service.guide;

import cn.rmfield.website.entity.*;
import cn.rmfield.website.entity.arknights.ArknightsStatistics;
import cn.rmfield.website.repository.arknights.ArknightsStatisticsRepostiory;
import cn.rmfield.website.repository.InvitationCodeRepository;
import cn.rmfield.website.repository.UserRepository;
import cn.rmfield.website.security.LoginUser;
import cn.rmfield.website.utils.JwtUtil;
import cn.rmfield.website.utils.RedisCache;
import cn.rmfield.website.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuideServiceImpl implements GuideService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvitationCodeRepository invitationCodeRepository;
    @Autowired
    private ArknightsStatisticsRepostiory arknightsStatisticsRepostiory;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    //注册
    @Override
    public ResponseResult register(RfUser userDomain) {
        //校验用户名唯一性
        RfUser findUserResult = userRepository.findByUsername(userDomain.getUsername());
        if(findUserResult != null){
            return new ResponseResult(5,"用户名已存在");
        }

        //检验邀请码
        InvitationCode invitationCode = invitationCodeRepository.findByCode(userDomain.getInvitationcode());
        if (Objects.isNull(invitationCode) || Objects.equals(invitationCode.getState(),0)) {
            return new ResponseResult(5,"邀请码错误");
        }

        //设置密码
        String secret = new BCryptPasswordEncoder().encode(userDomain.getPassword());
        userDomain.setPassword(secret);

        //设置权限
        List<Authority> authorityList = new ArrayList<>();
        /*Authority auth1 = new Authority();
        * auth1.setId(1);
        * auth1.setRolename("ROLE_ADMIN");
        * authorityList.add(auth1);
        * Authority auth2 = new Authority();
        * auth2.setId(2);
        * auth2.setRolename("ROLE_DBA");
        * authorityList.add(auth2);*/
        Authority auth3 = new Authority();
        auth3.setId(3);
        auth3.setRolename("ROLE_USER");
        authorityList.add(auth3);
        userDomain.setAuthorityList(authorityList);

        //设置方舟统计记录
        ArknightsStatistics arknightsStatistics = new ArknightsStatistics();
        arknightsStatisticsRepostiory.save(arknightsStatistics);
        userDomain.setArknightsStatistics(arknightsStatistics);

        try {
            userRepository.save(userDomain);
            invitationCode.setState(0);
            invitationCodeRepository.save(invitationCode);
            return new ResponseResult(1,"OK");
        }catch (Exception e){
            return new ResponseResult(5,"注册失败");
        }
    }


    //登录
    public ResponseResult login(RfUser rfUser){
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(rfUser.getUsername(),rfUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //认证失败
        if (Objects.isNull(authentication)){
            return new ResponseResult(5,"登录失败");
        }

        //认证通过，使用userid成jwt，jwt存入ResponseResult
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        String userid = Integer.toString(loginUser.getRfUser().getId());
        String jwt = JwtUtil.createJWT(userid);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",jwt);

        //用户信息存入redis，userid为key
        //在实体类中设置忽略字段无效，所以在这里手动设置空值
        for(Authority auth:loginUser.getRfUser().getAuthorityList()){
            auth.setUserList(null);
        }
        redisCache.setCacheObject("login:"+userid,loginUser);

        return new ResponseResult(0,"OK",tokenMap);
    }

    //注销
    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的userid
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        String userid = Integer.toString(loginUser.getRfUser().getId());

        //删除redis中的数据
        redisCache.deleteObject("login:" + userid);

        return new ResponseResult(1,"OK");
    }
}