package cn.rmfield.website.controller.guide;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.service.guide.GuideService;
import cn.rmfield.website.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GuideController {
    @Autowired
    private GuideService guideService;

    @CrossOrigin
    @PostMapping("/api/register")
    public ResponseResult register(@RequestBody RfUser rfUser){
        return guideService.register(rfUser);
    }

    @CrossOrigin
    @PostMapping("/api/login")
    public ResponseResult login(@RequestBody RfUser rfUser){

        return guideService.login(rfUser);
    }

    @CrossOrigin
    @GetMapping("/api/logout")
    public ResponseResult logout(){
        return guideService.logout();
    }
}
