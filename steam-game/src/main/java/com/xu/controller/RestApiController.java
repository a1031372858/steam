package com.xu.controller;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.xu.bean.custom.ResponseWebDto;
import com.xu.bean.model.RequestWebDto;
import com.xu.bean.model.SessionInfo;
import com.xu.utility.RedisUtil;
import com.xu.utility.SessionInfoHolder;
import com.xu.utility.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@RestController
public class RestApiController {
    private static final String REQUEST_HEADER_ITEM_KEY_SERVICE_NAME = "serviceName";
    private static final String REQUEST_HEADER_ITEM_KEY_METHOD_NAME = "methodName";
    private static final String REQUEST_HEADER_ITEM_KEY_WEBDTO_NAME = "webDtoName";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("reflection")
    public Object reflection(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestWebDto requestBody) {

        Object result;
        if (requestBody == null) {
        }
        try {
            result = doInvokeMethod(request, response, requestBody);
        } finally {
            SessionInfoHolder.clear();
        }
        return result;
    }


    @PostMapping("reflection")
    public Object doInvokeMethod(HttpServletRequest request, HttpServletResponse response, @RequestBody RequestWebDto requestBody) {
        String serviceName = request.getHeader(REQUEST_HEADER_ITEM_KEY_SERVICE_NAME);
        String methodName = request.getHeader(REQUEST_HEADER_ITEM_KEY_METHOD_NAME);
        String webDtoName = request.getHeader(REQUEST_HEADER_ITEM_KEY_WEBDTO_NAME);

        ResponseWebDto responseWebDto = new ResponseWebDto();
        SessionInfo sessionInfo = getSessionByRedis(requestBody.getTerminalId());
        if (sessionInfo == null) {
            responseWebDto.setStatus(403);
            return responseWebDto;
        }
        SessionInfoHolder.setSessionInfo(sessionInfo);
        try {
            Class<?> clazz = Class.forName(serviceName);
            Object service = applicationContext.getBean(clazz);
            Object result = null;
            if (service != null) {
                Object param = requestBody.getParam();
                if (param == null) {
                    Method method = clazz.getMethod(methodName);
                    result = method.invoke(service);
                } else {
                    Class<?> paramClazz = Class.forName(webDtoName);
                    Method method = clazz.getMethod(methodName, paramClazz);
                    result = method.invoke(service, param);
                }
            } else {
                responseWebDto.setStatus(404);
            }
            responseWebDto.setResponseContent(result);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return responseWebDto;
    }

    private SessionInfo getSessionByRedis(String terminalId) {
        if (StringUtils.isEmpty(terminalId)) return null;

        SessionInfo sessionInfo = JSON.parseObject((String) redisUtil.get(terminalId), SessionInfo.class);
        return sessionInfo;
    }
}
