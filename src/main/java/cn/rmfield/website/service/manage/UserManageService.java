package cn.rmfield.website.service.manage;

import cn.rmfield.website.entity.RfUser;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

public interface UserManageService {
    List<RfUser> getUserList();
    List<RfUser> getUserById(Integer id);
    List<RfUser> getUserByUsername(String username);
    List<RfUser> getUserByRealname(String realname);
    Boolean updateUser(UserDataForUpdate dataForUpdate);
}