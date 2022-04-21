package cn.rmfield.website.controller.user;

import cn.rmfield.website.service.arknights.ArknightsStatisticsService;
import cn.rmfield.website.utils.ResponseResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/api/arknights")
@PreAuthorize("hasRole('USER')")
public class ArknightsStatisticsController {
    @Autowired
    ArknightsStatisticsService akService;



    @PostMapping("")
    @ResponseBody
    public ResponseResult updateData(@RequestBody JSONObject jsonToken) {
        //更新数据
        Boolean tokenUpdateSuccess = akService.updateData((String) jsonToken.get("token"));
        if (!tokenUpdateSuccess) {
            return new ResponseResult(5,"更新数据失败");
        } else {
            return new ResponseResult(0,"OK");
        }
    }



    @GetMapping("/general")
    public ResponseResult getData() {
        try {
            return new ResponseResult(0,"OK",akService.getData());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(5,"获取数据失败");
        }
    }

    @GetMapping("/gacha")
    public ResponseResult gacha(){
        try {
            Map<String,List<Map<String,Object>>> gachaListMap = new HashMap<>();
            gachaListMap.put("gacha_list",akService.gachaDetail());
            return new ResponseResult(0,"OK",gachaListMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(5,"获取数据失败");
        }
    }

    @GetMapping("/diamond")
    public ResponseResult diamond(){
        try {
            Map<String,List<Map<String,Object>>> diamondListMap = new HashMap<>();
            diamondListMap.put("diamond_list",akService.diamondDetail());
            return new ResponseResult(0,"OK",diamondListMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(5,"获取数据失败");
        }
    }

    @GetMapping("/order")
    public ResponseResult order(){
        try {
            Map<String,List<Map<String,Object>>> orderListMap = new HashMap<>();
            orderListMap.put("order_list",akService.orderDetail());
            return new ResponseResult(0,"OK",orderListMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(5,"获取数据失败");
        }
    }
}
