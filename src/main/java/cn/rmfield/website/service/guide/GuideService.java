package cn.rmfield.website.service.guide;

import cn.rmfield.website.entity.RfUser;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GuideService {
    public String register(RfUser secUserDomain);
    public String loginSuccess(Model model);
    public String main(Model model);
    public String deniedAccess(Model model);
    public String logout(HttpServletRequest request, HttpServletResponse response);
}
