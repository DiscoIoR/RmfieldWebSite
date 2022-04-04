package cn.rmfield.website.service.manage;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
    public JSONObject getUserById(Integer id) {
        RfUser rfUser = userRepository.getById(id);
        UserDataForDisplay dataForManage = new UserDataForDisplay(rfUser);
        return JSONObject.parseObject(JSON.toJSONString(dataForManage));
    }

    @Override
    public JSONObject getUserByUsername(String username) {
        RfUser rfUser = userRepository.findByUsername(username);
        UserDataForDisplay dataForManage = new UserDataForDisplay(rfUser);
        return JSONObject.parseObject(JSON.toJSONString(dataForManage));
    }

    @Override
    public JSONArray getUserListByRealname(String realname) {
        List<RfUser> rfUserList = userRepository.findByRealname(realname);
        List<UserDataForDisplay> dataForManageList = new ArrayList<>();
        for (RfUser rfUser:rfUserList){
            dataForManageList.add(new UserDataForDisplay(rfUser));
        }
        return JSONArray.parseArray(JSON.toJSONString(dataForManageList));
    }

    @Override
    public Boolean updateUser(UserDataForUpdate dataForUpdate) {
        try {
            if(dataForUpdate.getId()!=null&&(!dataForUpdate.getUsername().equals(""))
                    &&dataForUpdate.getUsername()!=null&&(!dataForUpdate.getUsername().equals(""))
                    &&dataForUpdate.getRealname()!=null&&(!dataForUpdate.getRealname().equals(""))
                    &&dataForUpdate.getPassword()!=null&&(!dataForUpdate.getPassword().equals(""))
                    &&dataForUpdate.getROLE_ADMIN()!=null
                    &&dataForUpdate.getROLE_DBA()!=null
                    &&dataForUpdate.getROLE_USER()!=null
            ){
                RfUser rfUser = userRepository.getById(dataForUpdate.getId());
                rfUser.setUsername(dataForUpdate.getUsername());
                String secret = new BCryptPasswordEncoder().encode(dataForUpdate.getPassword());
                rfUser.setPassword(secret);
                rfUser.setRealname(dataForUpdate.getRealname());
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
                userRepository.save(rfUser);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
