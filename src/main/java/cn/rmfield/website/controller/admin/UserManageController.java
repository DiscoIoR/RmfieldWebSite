package cn.rmfield.website.controller.admin;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.service.manage.UserUpdateInfo;
import cn.rmfield.website.service.manage.UserManageService;
import cn.rmfield.website.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserManageController {
    @Autowired
    private UserManageService userManageService;


    @GetMapping("/user-ctrl")
    public ResponseResult getRfUser(
            @RequestParam(value = "id", required = false) String id_str,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "realname", required = false) String realname
    ) {
        List<RfUser> rfUserList;
        try {
            id_str = id_str.trim();
            username = username.trim();
            realname = realname.trim();

            Integer id = null;
            if (!id_str.equals("")) {
                try {
                    id = Integer.parseInt(id_str);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return new ResponseResult(5, "解析id时发生错误");
                }
            }

            if (id != null) {
                rfUserList = userManageService.getUserById(id);
                return new ResponseResult(0, "OK", rfUserList);
            }

            if (!username.equals("")) {
                rfUserList = userManageService.getUserByUsername(username);
                return new ResponseResult(0, "OK", rfUserList);
            }

            if (!realname.equals("")) {
                rfUserList = userManageService.getUserByRealname(realname);
                return new ResponseResult(0, "OK", rfUserList);
            }

            rfUserList = userManageService.getUserList();
            return new ResponseResult(0, "OK", rfUserList);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(5, "查询信息时发生错误");
        }
    }


    @PutMapping("/user-ctrl")
    public ResponseResult updateRfUser(@RequestBody UserUpdateInfo updateInfo) {
        return userManageService.updateUser(updateInfo);
    }
}
