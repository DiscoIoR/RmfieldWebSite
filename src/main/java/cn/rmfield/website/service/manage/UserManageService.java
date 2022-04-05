package cn.rmfield.website.service.manage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface UserManageService {
    JSONArray getUserList();
    JSONArray getUserById(Integer id);
    JSONArray getUserByUsername(String username);
    JSONArray getUserListByRealname(String realname);
    Boolean updateUser(UserDataForUpdate dataForUpdate);
    //Boolean deleteUser(String username);
}
