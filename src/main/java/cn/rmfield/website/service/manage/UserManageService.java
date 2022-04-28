package cn.rmfield.website.service.manage;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.utils.ResponseResult;

import java.util.List;

public interface UserManageService {
    List<RfUser> getUserList();

    List<RfUser> getUserById(Integer id);

    List<RfUser> getUserByUsername(String username);

    List<RfUser> getUserByRealname(String realname);

    ResponseResult updateUser(UserUpdateInfo dataForUpdate);
}