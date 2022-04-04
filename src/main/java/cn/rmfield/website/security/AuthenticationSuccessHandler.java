package cn.rmfield.website.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    //Spring Security 重定向策略
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    //重写handler方法，通过RedirectStrategy重定向到指定的URL
    @Override
    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        //根据当前用户获取正确URL
        String targetUL = getTargetURL(authentication);
        //重定向到URL
        redirectStrategy.sendRedirect(request,response,targetUL);
    }

    protected String getTargetURL(Authentication authentication){
        String url = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for(GrantedAuthority au:authorities){
            roles.add(au.getAuthority());
        }
        if(roles.contains("ROLE_USER")){
            url = "/user";
        }else if(roles.contains("ROLE_ADMIN")){
            url = "/admin";
        }else {
            url = "/access-denied";
        }
        return url;
    }
}
