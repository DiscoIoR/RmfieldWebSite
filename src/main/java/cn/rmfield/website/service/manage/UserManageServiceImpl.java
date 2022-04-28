package cn.rmfield.website.service.manage;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserManageServiceImpl implements UserManageService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RfUser> getUserList() {
        List<RfUser> rfUserList = userRepository.findAll();
        for (RfUser rfUser:rfUserList){
            setFieldNull(rfUser);
        }
        return rfUserList;
    }

    @Override
    public List<RfUser> getUserById(Integer id) {
        List<RfUser> rfUserList = new ArrayList<>();
        Optional<RfUser> rfUserOptional = userRepository.findById(id);
        if (rfUserOptional.isPresent()) {
            RfUser rfUser = rfUserOptional.get();
            setFieldNull(rfUser);
            rfUserList.add(rfUser);
            return rfUserList;
        }
        return rfUserList;
    }

    @Override
    public List<RfUser> getUserByUsername(String username) {
        List<RfUser> rfUserList = new ArrayList<>();
        RfUser rfUser;
        try {
            rfUser = userRepository.findByUsername(username);
            if (rfUser == null){
                return rfUserList;
            }
            setFieldNull(rfUser);
            rfUserList.add(rfUser);
            return rfUserList;
        }catch (RuntimeException e){
            e.printStackTrace();
            return rfUserList;
        }
    }

    @Override
    public List<RfUser> getUserByRealname(String realname) {
        List<RfUser> rfUserList = userRepository.findByRealname(realname);
        for (RfUser rfUser : rfUserList) {
            setFieldNull(rfUser);
        }
        return rfUserList;
    }

    private void setFieldNull(RfUser rfUser){
        rfUser.setPassword(null);
        rfUser.setArknightsStatistics(null);
        for(Authority authority:rfUser.getAuthorityList()){
            authority.setUserList(null);
        }
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
