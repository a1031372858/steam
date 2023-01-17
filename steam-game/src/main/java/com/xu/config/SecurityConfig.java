package com.xu.config;

import com.xu.service.impl.SteamUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SteamUserDetailServiceImpl steamUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().defaultSuccessUrl("/")
                .and().authorizeRequests()
                //首页所有人可以访问，功能页只有对应有权限的人才能访问
                .antMatchers("/").permitAll()
                .antMatchers("/game/**").hasAuthority("vip1")
                .antMatchers("/test/**")
                //使用hasRole的时候，如果权限前面没有带ROLE_的话，就会自动补上，你的用户权限前面没有ROLE_会判断没有权限
                .hasAuthority("vip1")
                //注销，开启了注销功能
                .and().logout().logoutSuccessUrl("/successLogout")
                .and().exceptionHandling().accessDeniedPage("/home.html")
                .and().rememberMe()
                //防止网站攻击： get,post
                .and().csrf().disable();
    }
//
//    //认证
//    //密码编码：PasswordEncoder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这些数据正常应该从数据库中读
        //BCryptPasswordEncoder加密类，encode()编码，passwordEncoder()把客户端传过来的密码加密
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("lzj").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip3")
//                .and()
//                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
//                .and()
//                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");

        auth.userDetailsService(steamUserDetailService).passwordEncoder(passwordEncoder());
    }
}
