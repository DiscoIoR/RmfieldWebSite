package cn.rmfield.website.service.manage;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManageServiceImpl implements UserManageService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public JSONArray getUserList() {
        List<RfUser> rfUserList = userRepository.findAll();
        List<UserDataForDisplay> dataForDisplayList = new ArrayList<>();
        for (RfUser rfUser:rfUserList){
            dataForDisplayList.add(new UserDataForDisplay(rfUser));
        }
        return JSONArray.parseArray(JSON.toJSONString(dataForDisplayList));
    }

    @Override
    public JSONArray getUserById(Integer id) {
        RfUser rfUser = userRepository.getById(id);
        List<UserDataForDisplay> dataForDisplayList = new ArrayList<>();
        dataForDisplayList.add(new UserDataForDisplay(rfUser));
        return JSONArray.parseArray(JSON.toJSONString(dataForDisplayList));
    }

    @Override
    public JSONArray getUserByUsername(String username) {
        RfUser rfUser = userRepository.findByUsername(username);
        if(rfUser==null){
            return null;
        }
        List<UserDataForDisplay> dataForDisplayList = new ArrayList<>();
        dataForDisplayList.add(new UserDataForDisplay(rfUser));
        return JSONArray.parseArray(JSON.toJSONString(dataForDisplayList));
    }

    @Override
    public JSONArray getUserListByRealname(String realname) {
        List<RfUser> rfUserList = userRepository.findByRealname(realname);
        List<UserDataForDisplay> dataForDisplayList = new ArrayList<>();
        for (RfUser rfUser:rfUserList){
            dataForDisplayList.add(new UserDataForDisplay(rfUser));
        }
        if(dataForDisplayList.size()==0){
            return null;
        }
        return JSONArray.parseArray(JSON.toJSONString(dataForDisplayList));
    }

    @Override
    public Boolean updateUser(UserDataForUpdate dataForUpdate) {
        try {
            //校验id是否为空
            if(dataForUpdate.getId()==null){
                return false;
            }
            RfUser rfUser = userRepository.getById(dataForUpdate.getId());
            //判断是否更新用户名
            if(dataForUpdate.getUsername()!=null&&(!dataForUpdate.getUsername().equals(""))){
                rfUser.setUsername(dataForUpdate.getUsername());
            }
            //判断是否更新别名
            if(dataForUpdate.getRealname()!=null&&(!dataForUpdate.getRealname().equals(""))){
                rfUser.setRealname(dataForUpdate.getRealname());
            }
            //判断是否更新密码
            if(dataForUpdate.getPassword()!=null&&(!dataForUpdate.getPassword().equals(""))){
                String secret = new BCryptPasswordEncoder().encode(dataForUpdate.getPassword());
                rfUser.setPassword(secret);
            }
            //校验是否设置里权限，并生成新的权限列表
            if(dataForUpdate.getROLE_ADMIN()!=null
                    &&dataForUpdate.getROLE_DBA()!=null
                    &&dataForUpdate.getROLE_USER()!=null){
                List<Authority> authorityList = new ArrayList<>();
                if (dataForUpdate.getROLE_ADMIN()){
                    Authority auth = new Authority();
                    auth.setId(1);
                    auth.setRolename("ROLE_ADMIN");
                    authorityList.add(auth);
                }
                if (dataForUpdate.getROLE_DBA()){
                    Authority auth = new Authority();
                    auth.setId(2);
                    auth.setRolename("ROLE_DBA");
                    authorityList.add(auth);
                }
                if (dataForUpdate.getROLE_USER()){
                    Authority auth = new Authority();
                    auth.setId(3);
                    auth.setRolename("ROLE_USER");
                    authorityList.add(auth);
                }
                rfUser.setAuthorityList(authorityList);
            }else {
                return false;
            }
            userRepository.save(rfUser);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
