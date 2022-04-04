package cn.rmfield.website.controller.guide;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.service.guide.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuideController {
    @Autowired
    private GuideService guideService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String toRegister(@ModelAttribute("userDomain") RfUser userDomain) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDomain") RfUser userDomain) {
        return guideService.register(userDomain);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/user")
    public String loginSuccess(){
        return "user/user";
    }

    @RequestMapping("/admin")
    public String main(Model model){
        return "admin/admin";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "login";
    }

    @RequestMapping("/access-denied")
    public String deniedAccess(){
        return "access-denied";
    }
}
