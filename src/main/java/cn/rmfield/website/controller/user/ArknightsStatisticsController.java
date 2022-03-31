package cn.rmfield.website.controller.user;

import cn.rmfield.website.service.ArknightsStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class ArknightsStatisticsController {
    @Autowired
    ArknightsStatisticsService akService;

    @GetMapping("/user/ArknightsStatisstics")
    public String arknightsStatisstics(@RequestParam(value = "token",required = false)String token,Model model){
        if(Objects.equals(token, "") || token == null){
            model.addAttribute("result",akService.updateData(model,token));
        }else{
            model.addAttribute("result",akService.getData(model));
        }

        return "user/arknightsStatistics";
    }


}
