package cn.rmfield.website.controller.user;

import cn.rmfield.website.service.arknights.ArknightsStatisticsService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Tk{
    private String token;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}

@Controller
@RequestMapping("/user")
public class ArknightsStatisticsController {
    @Autowired
    ArknightsStatisticsService akService;

    //概况页面
    @GetMapping("/arknights-statistics")
    public String arknightsStatisstics() {
        return "user/arknights-statistics";
    }

    @PostMapping("/api/arknights-statistics/general")
    @ResponseBody
    public Map<String,String> updateData(@RequestBody JSONObject jsonToken) {
        //更新数据
        Boolean tokenUpdateSuccess = akService.updateData((String) jsonToken.get("token"));
        Map<String,String> result = new HashMap<>();
        if (!tokenUpdateSuccess) {
            result.put("updateResult","更新数据失败,可能是token不正确");
        } else {
            result.put("updateResult","更新数据成功");
        }
        return result;
    }

    @GetMapping("/api/arknights-statistics/general")
    @ResponseBody
    public Map<String, Object> getData() {
        //查询数据
        return akService.getData();
    }

    //详细数据页面
    @GetMapping("/arknight-statistics/details")
    public String details(){
        return "user/arknights-statistics-details";
    }

    @GetMapping("/api/arknights-statistics/gacha")
    public List<Map<String,Object>> gacha(){

        return null;
    }

    @GetMapping("/api/arknights-statistics/diamond")
    public List<Map<String,Object>> diamond(){

        return null;
    }

    @GetMapping("/api/arknights-statistics/order")
    public List<Map<String,Object>> order(){

        return null;
    }
}
