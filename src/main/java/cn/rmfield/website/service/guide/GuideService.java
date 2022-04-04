package cn.rmfield.website.service.guide;

import cn.rmfield.website.entity.RfUser;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GuideService {
    String register(RfUser secUserDomain);
    String loginSuccess(Model model);
    String main(Model model);
    String logout(HttpServletRequest request, HttpServletResponse response);
}
