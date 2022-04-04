package cn.rmfield.website.controller.guide;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.service.guide.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class GuideController {
    @Autowired
    private GuideService guideService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/toRegister")
    public String toRegister(@ModelAttribute("userDomain") RfUser userDomain) {
        return "register";
    }

    @RequestMapping("/register")
    public String register(@ModelAttribute("userDomain") RfUser userDomain) {
        return guideService.register(userDomain);
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/user/loginSuccess")
    public String loginSuccess(Model model){
        return guideService.loginSuccess(model);
    }

    @RequestMapping("/admin/main")
    public String main(Model model){
        return guideService.main(model);
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        return guideService.logout(request,response);
    }

    @RequestMapping("/access-denied")
    public String deniedAccess(){
        return "access-denied";
    }
}
