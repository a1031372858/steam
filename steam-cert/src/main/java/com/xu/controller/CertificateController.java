package com.xu.controller;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.bean.LoginSession;
import com.xu.bean.Ticket;
import com.xu.bean.UserDetail;
import com.xu.bean.custom.ResponseWebDto;
import com.xu.bean.custom.ServiceResult;
import com.xu.common.Constant;
import com.xu.config.ConstantConfig;
import com.xu.service.CertService;
import com.xu.service.TicketService;
import com.xu.service.UserService;
import com.xu.service.dubboImpl.DubboCertServiceImpl;
import com.xu.utility.CookieUtils;
import com.xu.utility.RedisUtil;
import com.xu.utility.RequestHeaderUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Api("认证中心")
@Controller
@RequestMapping("cert")
public class CertificateController {

    private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);

    @Autowired
    private HttpServletRequest rq;
    @Autowired
    private HttpServletResponse rp;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CertService certService;


    @Autowired
    private TicketService ticketService;


    /**
     * 测试
     *
     * @param phone
     * @param userPassword
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("login")
    @ResponseBody
    @Transactional
    public ResponseWebDto login(@ApiParam("手机号") String phone, @ApiParam("密码") String userPassword, @ApiParam("请求路径") String targetUrl) {
        ResponseWebDto responseWebDto = new ResponseWebDto();
        logger.info("入参：用户名：" + phone + "，密码：" + userPassword + ",要登录的网址：" + targetUrl + "。");
        String referer = rq.getHeader("Referer");
        if (targetUrl == null && !StringUtils.isEmpty(referer)) {
            targetUrl= RequestHeaderUtility.getUrlParamByName(referer,"targetUrl");
            if (StringUtils.isEmpty(targetUrl)) {
                targetUrl = ConstantConfig.HOME_RUL;
            }
            logger.info("referer中的targetUrl:" + targetUrl);
        }

        RequestHeaderUtility.printHostIp(rq,logger);
        String hostIp = RequestHeaderUtility.getClientIpAddress(rq, logger);
        if (hostIp == null) hostIp = "";

        ServiceResult<UserDetail> serviceResult =userService.loginByPassword(phone,userPassword);

        if(serviceResult.getStatus()==1&&null != serviceResult.getResult()){
            UserDetail userDetail=serviceResult.getResult();
            //手机号密码正确的场合


            //追加cookie，在Redis记录登录状态
            //cookie追加
            String sessionId = UUID.randomUUID().toString();
            certService.insertLoginSessionToRedis(Constant.CERT_SERVER,userDetail.getUserId(),sessionId,hostIp);
            Cookie loginCodeCookie = CookieUtils.setCookie(Constant.SESSIONID, sessionId, "/", 60 * 30);
            rp.addCookie(loginCodeCookie);


            //生成允许登录的ticket
            ServiceResult<Ticket> getTicketResult= certService.getTicket(Constant.CERT_SERVER,userDetail.getUserId(),hostIp);
            if(getTicketResult.getStatus()==1&&getTicketResult.getResult()!=null){

                responseWebDto.setStatus(HttpStatus.OK.value());
                responseWebDto.setMessage(getTicketResult.getMessage());
                //返回带ticket的目标url地址
                responseWebDto.setResponseContent(targetUrl + "?ticket=" + getTicketResult.getResult().getTicketCode());
            }else{
                responseWebDto.setStatus(HttpStatus.OK.value());
                responseWebDto.setMessage(getTicketResult.getMessage());
            }

        }else{
            responseWebDto.setStatus(HttpStatus.OK.value());
            responseWebDto.setMessage(serviceResult.getMessage());
            logger.info(serviceResult.getMessage());
        }

        return responseWebDto;
    }

    @ApiOperation("登录")
    @GetMapping("login")
    public String AutoLogin(String targetUrl) {
        logger.info("入参：要登录的网址：" + targetUrl);
        String path = rq.getContextPath();
        //打印hostIp
        RequestHeaderUtility.printHostIp(rq,logger);

        String hostIp = RequestHeaderUtility.getClientIpAddress(rq, logger);
        if(hostIp==null)hostIp="";

        String sessionId = CookieUtils.getCookieValue(rq, Constant.SESSIONID);
        if (!StringUtils.isEmpty(sessionId)) {
            LoginSession loginSession =null;
            try {
                loginSession = (LoginSession)redisUtil.get(sessionId+Constant.REDIS_SEPARATOR+Constant.CERT_SERVER);
            }catch (Exception e){
                logger.error(e.toString());
            }

            if (loginSession != null) {
                logger.info("AutoLogin:自动登录成功");

                ServiceResult<Ticket> ticketResult = certService.getTicket(Constant.CERT_SERVER,loginSession.getUserId(),hostIp);
                if(ticketResult.getStatus()==1&& null != ticketResult.getResult()){
                    return "redirect:" + targetUrl + "?ticket=" + ticketResult.getResult().getTicketCode();
                }else{
                    return "redirect:" + ConstantConfig.LOGIN_RUL + "?targetUrl=" + targetUrl;
                }

            }
        }
        //没有sessionId或sessionId无效的场合，重定向至登录画面
        return "redirect:" + ConstantConfig.LOGIN_RUL + "?targetUrl=" + targetUrl;
    }

    @GetMapping("test")
    public String test(){
        return userService.loginByPassword("15797704512","123456").toString();
    }



    @ResponseBody
    @GetMapping("ticket/checkTicket")
    public ResponseWebDto checkTicket(String ticketCode, String hostIp) {
        logger.info(rq.getRequestURI()+",paramter:ticketCode:"+rq.getParameter("ticketCode")+"header:"+rq.getHeader("ticketCode"));

        return ticketService.checkTicket(ticketCode,hostIp);
    }


    @ResponseBody
    @GetMapping("test2")
    public String test2() {
        return "Hello Spring Cloud";
    }

}
