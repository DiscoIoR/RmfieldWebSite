package cn.rmfield.website.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfigerAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Configuration
    public static class Provider {
        @Autowired
        private UserSecurityService userSecurityService;
        private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(){
            @Bean
            public PasswordEncoder passwordEncoder(){
                return new BCryptPasswordEncoder();
            }
        };
        @Bean
        public AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider provide = new DaoAuthenticationProvider();
            provide.setHideUserNotFoundExceptions(false);
            provide.setUserDetailsService(userSecurityService);
            provide.setPasswordEncoder(passwordEncoder);
            return provide;
        }
    }

    //用户认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        System.out.println("configure(AuthenticationManagerBuilder auth)");
        //设置认证方式
        auth.authenticationProvider(authenticationProvider);
    }

    //请求授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure(HttpSecurity http)");
        http.authorizeRequests()
                //页面访问权限
                .antMatchers( "/css/**", "/fonts/**", "/js/**").permitAll()
                .antMatchers("/toLogin", "/toRegister", "/", "/login", "/register").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "DBA")
                .anyRequest().authenticated()
                .and()
                //登录
                .formLogin()
                    .loginPage("/login").successHandler(authenticationSuccessHandler)
                    .usernameParameter("username").passwordParameter("password")
                .failureUrl("/login?failed")
                .and()
                //注销
                .logout().permitAll()
                .and()
                //指定权限限制页面
                .exceptionHandling().accessDeniedPage("/deniedAccess");
    }
}

