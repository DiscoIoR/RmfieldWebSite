package cn.rmfield.website.controller.user;

import cn.rmfield.website.service.arknights_data.ArknightsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class ArknightsStatisticsController {
    @Autowired
    ArknightsStatisticsService akService;

    @GetMapping("/api/ArknightsStatistics/updateData")
    @ResponseBody
    public String updateData(@RequestParam(value = "token") String token) {
        //更新数据
        Boolean tokenUpdateSuccess = akService.updateData(token);
        if (!tokenUpdateSuccess) {
            return "更新数据失败";
        } else {
            return "更新数据成功";
        }
    }

    @GetMapping("/api/ArknightsStatistics/getData")
    @ResponseBody
    public Map<String, Object> getData() {
        //查询数据
        return akService.getData();
    }


    @GetMapping("/ArknightsStatistics")
    public String arknightsStatisstics() {
        return "user/arknightsStatistics";
    }
}
