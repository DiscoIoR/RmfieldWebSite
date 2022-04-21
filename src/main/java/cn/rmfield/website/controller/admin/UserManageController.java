package cn.rmfield.website.controller.admin;

import cn.rmfield.website.service.manage.UserDataForUpdate;
import cn.rmfield.website.service.manage.UserManageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//TODO 和新版本前端对接

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class UserManageController {
    @Autowired
    private UserManageService userManageService;

    @GetMapping("rfuser")
    public String userManage(){
        return "admin/user-manage-panel";
    }

    @GetMapping("/api/rfuser/list")
    @ResponseBody
    public JSONArray getRfUserList(@RequestParam(value = "realname",required = false) String realname){
        if(realname!=null){
            return userManageService.getUserListByRealname(realname);
        }
        return userManageService.getUserList();
    }

    @GetMapping("/api/rfuser")
    @ResponseBody
    public JSONArray getRfUser(
            @RequestParam(value = "id",required = false) Integer id,
            @RequestParam(value = "username",required = false) String username
    ) {
        if(id!=null&&(username==null|| username.equals(""))){
            return userManageService.getUserById(id);
        }else if (id==null&&(username!=null && !username.equals(""))){
            return userManageService.getUserByUsername(username);
        }
        return null;
    }

    @PostMapping("/api/rfuser")
    @ResponseBody
    public JSONArray updateRfUser(@RequestBody JSONObject jsonObject){
        UserDataForUpdate dataForUpdate = JSON.parseObject(jsonObject.toString(), new TypeReference<>(){});
        if(userManageService.updateUser(dataForUpdate)){
            return userManageService.getUserById(dataForUpdate.getId());
        }
        return null;
    }
}
