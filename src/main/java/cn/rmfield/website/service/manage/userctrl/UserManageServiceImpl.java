package cn.rmfield.website.service.manage.userctrl;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.UserRepository;
import cn.rmfield.website.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RfUser> getUserList() {
        List<RfUser> rfUserList = userRepository.findAll();
        for (RfUser rfUser : rfUserList) {
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
            if (rfUser == null) {
                return rfUserList;
            }
            setFieldNull(rfUser);
            rfUserList.add(rfUser);
            return rfUserList;
        } catch (RuntimeException e) {
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

    private void setFieldNull(RfUser rfUser) {
        rfUser.setPassword(null);
        rfUser.setArknightsStatistics(null);
        for (Authority authority : rfUser.getAuthorityList()) {
            authority.setUserList(null);
        }
    }

    @Override
    public ResponseResult updateUser(UserUpdateInfo dataForUpdate) {
        try {
            //??????id????????????
            Integer id = dataForUpdate.getId();
            if (id == null) {
                return new ResponseResult(5, "id????????????");
            }
            Optional<RfUser> rfUserOptional = userRepository.findById(dataForUpdate.getId());
            RfUser rfUser;
            if (rfUserOptional.isPresent()) {
                rfUser = rfUserOptional.get();
            } else {
                return new ResponseResult(5, "??????????????????");
            }

            //?????????????????????????????????
            List<Authority> authorityList = rfUser.getAuthorityList();
            List<String> authorityNameList = new ArrayList<>();
            for (Authority authority : authorityList) {
                authorityNameList.add(authority.getRolename());
            }
            if (authorityNameList.contains("ROLE_ADMIN")) {
                return new ResponseResult(5, "??????????????????????????????");
            }

            //???????????????????????????
            if (dataForUpdate.getUsername() != null && (!dataForUpdate.getUsername().equals(""))) {
                rfUser.setUsername(dataForUpdate.getUsername());
            }
            //????????????????????????
            if (dataForUpdate.getRealname() != null && (!dataForUpdate.getRealname().equals(""))) {
                rfUser.setRealname(dataForUpdate.getRealname());
            }
            //????????????????????????
            if (dataForUpdate.getPassword() != null && (!dataForUpdate.getPassword().equals(""))) {
                String secret = new BCryptPasswordEncoder().encode(dataForUpdate.getPassword());
                rfUser.setPassword(secret);
            }
            //?????????????????????????????????????????????????????????
            if (dataForUpdate.getROLE_ADMIN() != null
                    && dataForUpdate.getROLE_DBA() != null
                    && dataForUpdate.getROLE_USER() != null) {
                authorityList.clear();
                if (dataForUpdate.getROLE_ADMIN()) {
                    Authority auth = new Authority();
                    auth.setId(1);
                    auth.setRolename("ROLE_ADMIN");
                    authorityList.add(auth);
                }
                if (dataForUpdate.getROLE_DBA()) {
                    Authority auth = new Authority();
                    auth.setId(2);
                    auth.setRolename("ROLE_DBA");
                    authorityList.add(auth);
                }
                if (dataForUpdate.getROLE_USER()) {
                    Authority auth = new Authority();
                    auth.setId(3);
                    auth.setRolename("ROLE_USER");
                    authorityList.add(auth);
                }
                rfUser.setAuthorityList(authorityList);
            } else {
                return new ResponseResult(5, "??????????????????");
            }
            userRepository.save(rfUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(5, "?????????????????????????????????");
        }
        return new ResponseResult(0, "OK", getUserById(dataForUpdate.getId()));
    }
}
