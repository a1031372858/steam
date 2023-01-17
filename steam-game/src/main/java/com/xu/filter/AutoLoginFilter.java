package com.xu.filter;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.bean.LoginSession;
import com.xu.bean.UserDetail;
import com.xu.bean.custom.ResponseWebDto;
import com.xu.common.Constant;
import com.xu.service.DubboCertService;
import com.xu.utility.CookieUtils;
import com.xu.utility.RedisUtil;
import com.xu.utility.RequestHeaderUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

//@WebFilter("/ranks/*")
public class AutoLoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AutoLoginFilter.class);

    @Autowired
    private RedisUtil redisUtil;

//    @Reference
//    private DubboCertService dubboCertService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rp = (HttpServletResponse) response;
        UserDetail userDetail = null;

        RequestHeaderUtility.printHostIp(rq,logger);

        String hostIp = RequestHeaderUtility.getClientIpAddress(rq, logger);
        if (hostIp == null) hostIp = "";

        //获取sessionId
        String sessionId = CookieUtils.getCookieValue(rq, Constant.SESSIONID2);
        if(!StringUtils.isEmpty(sessionId)){
            LoginSession loginSession = null;
            try {
                loginSession = (LoginSession) redisUtil.get(sessionId+Constant.REDIS_SEPARATOR+Constant.GAME_SERVER);
            }catch (Exception e){
                logger.error(e.toString());
            }
            //先检查sessionId是否有效
            if (loginSession != null) {
                logger.info("自动登录成功");
                chain.doFilter(request, response);
                return;
            }
        }

        //如果没有sessionId，再看有没有带ticket过来
        String ticket = rq.getParameter("ticket");
        //如果有ticket，检查ticket是否有效
        if(null !=ticket){
            try {
                ResponseWebDto responseWebDto  = restTemplate.getForObject("http://steam-cert/cert/ticket/checket",ResponseWebDto.class,ticket,hostIp);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
                if(responseWebDto!=null && responseWebDto.getStatus()==1){
                    userDetail = objectMapper.readValue(responseWebDto.getResponseContent().toString(), UserDetail.class);
                }
            }catch (Exception e){
                logger.error("AutoLoginFilter " + e.toString());
            }

        }

        if (null != userDetail) {
            //如果有效的话，加上cookie。记录登录状态，允许访问。
            sessionId = UUID.randomUUID().toString();
            Cookie loginCodeCookie = CookieUtils.setCookie(Constant.SESSIONID2, sessionId, "/", 60 * 30);
            rp.addCookie(loginCodeCookie);


            LoginSession loginSession = new LoginSession();
            loginSession.setUserId(userDetail.getUserId());
            loginSession.setSessionId(sessionId);
            loginSession.setHostIp(hostIp);
            loginSession.setServerId(Constant.GAME_SERVER);

            redisUtil.set(sessionId+Constant.REDIS_SEPARATOR+Constant.GAME_SERVER,loginSession,60*30L);

            rp.sendRedirect(rq.getRequestURL().toString());
        } else {
            //无效的话前往前往认证中心
            rp.sendRedirect(Constant.DOMAIN+"/cert/login?targetUrl=" + rq.getRequestURL());
        }

    }

    @Override
    public void destroy() {

    }


}
