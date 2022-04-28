package cn.rmfield.website.controller.admin;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.service.manage.UserDataForUpdate;
import cn.rmfield.website.service.manage.UserManageService;
import cn.rmfield.website.utils.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO 和新版本前端对接

@RestController
@RequestMapping("/admin/api")
@PreAuthorize("hasRole('USER')")
public class UserManageController {
    @Autowired
    private UserManageService userManageService;


    @GetMapping("/user-ctrl")
    public ResponseResult getRfUser(
            @RequestParam(value = "id",required = false) String id_str,
            @RequestParam(value = "username",required = false) String username,
            @RequestParam(value = "realname",required = false) String realname
    ) {
        List<RfUser> rfUserList = null;
        try {
            id_str = id_str.trim();
            username = username.trim();
            realname = realname.trim();

            Integer id = null;
            if (!id_str.equals("")) {
                try {
                    id = Integer.parseInt(id_str);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return new ResponseResult(5,"解析id时发生错误");
                }
            }

            //TODO userManageService.getUserById(id);
            if(id != null){
                rfUserList = userManageService.getUserById(id);
                return new ResponseResult(0,"OK",rfUserList);
            }

            //TODO userManageService.getUserByUsername(username);
            if(!username.equals("")){
                rfUserList = userManageService.getUserByUsername(username);
                return new ResponseResult(0,"OK",rfUserList);
            }

            //TODO getUserByRealname(realname);
            if(!realname.equals("")){
                rfUserList = userManageService.getUserByRealname(realname);
                return new ResponseResult(0,"OK",rfUserList);
            }

            //TODO getUserList();
            rfUserList = userManageService.getUserList();
            return new ResponseResult(0,"OK",rfUserList);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(0,"OK",rfUserList);
        }
    }


    @PutMapping("/user-ctrl")
    public ResponseResult updateRfUser(@RequestBody JSONObject jsonObject){
        UserDataForUpdate dataForUpdate = JSON.parseObject(jsonObject.toString(), new TypeReference<>(){});
        if(userManageService.updateUser(dataForUpdate)){
            userManageService.getUserById(dataForUpdate.getId());
        }
        return new ResponseResult(0,"",null);
    }
}
