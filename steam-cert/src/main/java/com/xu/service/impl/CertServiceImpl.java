package com.xu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.xu.bean.LoginSession;
import com.xu.bean.Ticket;
import com.xu.bean.custom.ServiceResult;
import com.xu.common.Constant;
import com.xu.mapper.UserDetailMapper;
import com.xu.service.CertService;
import com.xu.utility.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CertServiceImpl implements CertService {
    private static final Logger logger = LoggerFactory.getLogger(CertServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Override
    public void insertLoginSessionToRedis(int serverId,long userId,String sessionId,String hostIp) {
        if(null == hostIp||null == sessionId|| 0 == userId)return;

        //在Redis将该用户设置为已登录状态
        LoginSession loginSession = new LoginSession();
        loginSession.setUserId(userId);
        loginSession.setServerId(serverId);
        loginSession.setSessionId(sessionId);
        loginSession.setHostIp(hostIp);
        redisUtil.set(sessionId+Constant.REDIS_SEPARATOR+Constant.CERT_SERVER,loginSession,60*30L);
    }

    @Override
    public void insertLoginSession(int serverId, long userId, String sessionId, String hostIp) {

    }

    @Override
    public ServiceResult<Ticket> getTicket(int serverId, long userId, String hostIp) {

        ServiceResult<Ticket> result = new ServiceResult<>();
        if(0==userId||null == hostIp){
            result.setStatus(2);
            result.setMessage("参数不对");
            return result;
        }
        String ticketCode = UUID.randomUUID().toString();
        Ticket ticket = new Ticket();
        ticket.setTicketCode(ticketCode);
        ticket.setServerId(Constant.CERT_SERVER);
        ticket.setHostIp(hostIp);
        ticket.setUserId(userId);
        redisUtil.set(ticketCode,ticket,60L);

        result.setStatus(1);
        result.setResult(ticket);
        return result;
    }

    @Override
    public String test(String msg) {
        if(StringUtils.isEmpty(msg)){
            msg="";
        }
        return msg;
    }
}
