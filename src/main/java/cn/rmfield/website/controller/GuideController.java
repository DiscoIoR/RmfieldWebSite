package cn.rmfield.website.controller;

import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GuideController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "/login";
    }

    @RequestMapping("/toRegister")
    public String toRegister(@ModelAttribute("userDomain") RfUser userDomain) {
        return "/register";
    }

    @RequestMapping("/register")
    public String register(@ModelAttribute("userDomain") RfUser userDomain) {
        return userService.register(userDomain);
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/user/loginSuccess")
    public String loginSuccess(Model model){
        return userService.loginSuccess(model);
    }

    @RequestMapping("/admin/main")
    public String main(Model model){
        return userService.main(model);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        return userService.logout(request,response);
    }

    @RequestMapping("/deniedAccess")
    public String deniedAccess(Model model){
        return userService.deniedAccess(model);
    }
}
