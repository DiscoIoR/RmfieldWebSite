package cn.rmfield.website.controller.user;

import cn.rmfield.website.service.arknights_data.ArknightsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Objects;

@Controller
public class ArknightsStatisticsController {
    @Autowired
    ArknightsStatisticsService akService;

    @GetMapping("/user/ArknightsStatisstics")
    public String arknightsStatisstics(@RequestParam(value = "token",required = false)String token,Model model){
        if(!(Objects.equals(token, "") || token == null)){
            akService.updateData(token);
        }
        Map<String,Object> data = akService.getData();
        model.addAttribute("sixCount",data.get("sixCount"));
        model.addAttribute("fiveCount",data.get("fiveCount"));
        model.addAttribute("fourCount",data.get("fourCount"));
        model.addAttribute("threeCount",data.get("threeCount"));
        model.addAttribute("sixRate",data.get("sixRate"));
        model.addAttribute("fiveRate",data.get("fiveRate"));
        model.addAttribute("fourRate",data.get("fourRate"));
        model.addAttribute("threeRate",data.get("threeRate"));
        model.addAttribute("total",data.get("total"));

        return "user/arknightsStatistics";
    }


}
