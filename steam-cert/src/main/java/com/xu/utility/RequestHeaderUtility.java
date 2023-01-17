package com.xu.utility;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHeaderUtility {

    /**
     * 获取客户端的IP地址<br/>
     * 注意本地测试访问项目地址时，浏览器请求不要用 localhost，请用本机IP；否则，取不到 IP
     *
     * @author east7
     * @date 2019年12月03日
     * @return String 真实IP地址
     */
    public static String getClientIpAddress(HttpServletRequest request, Logger logger) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String headerName = "x-forwarded-for";
        String ip = request.getHeader(headerName);
        if (null != ip && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个IP才是真实IP,它们按照英文逗号','分割
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (checkIp(ip)) {
            headerName = "Proxy-Client-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "WL-Proxy-Client-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "HTTP_CLIENT_IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "HTTP_X_FORWARDED_FOR";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "X-Real-IP";
            ip = request.getHeader(headerName);
        }
        logger.info("RequestHeaderUtility ClientIp:"+ip+",HeaderName:"+headerName+"。");
        return ip;
    }

    private static boolean checkIp(String ip) {
        if (null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }


    public static Map<String, String> getUrlPramNameAndValue(String url) {
        String regEx = "(\\?|&+)(.+?)=([^&]*)";//匹配参数名和参数值的正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        Map<String, String> paramMap = new HashMap<String, String>();
        while (m.find()) {
            String paramName = m.group(2);//获取参数名
            String paramVal = m.group(3);//获取参数值
            paramMap.put(paramName, paramVal);
        }
        return paramMap;
    }

    public static String getUrlParamByName(String url,String name){
        Map<String, String> paramMap = RequestHeaderUtility.getUrlPramNameAndValue(url);
        String paramValue = paramMap.get(name);
        if (StringUtils.isEmpty(paramValue)) {
            paramValue = "";
        }
        return paramValue;
    }

    public static void printHostIp(HttpServletRequest rq, Logger logger) {
        String uri = rq.getRequestURI();
        String host = rq.getHeader("Host");
        String xRealIP = rq.getHeader("X-Real-IP");
        String xRealPort = rq.getHeader("X-Real-Port");
        String xForwardedFor = rq.getHeader("X-Forwarded-For");
        logger.info("请求源记录： uri:" + uri + ",host:" + host + ",xRealIP:" + xRealIP + ",xRealPort:" + xRealPort + ",xForwardedFor:" + xForwardedFor + "。");
    }
}
