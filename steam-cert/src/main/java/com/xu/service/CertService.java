package com.xu.service;

import com.xu.bean.Ticket;
import com.xu.bean.custom.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public interface CertService {

    void insertLoginSessionToRedis(int serverId,long userId,String sessionId,String hostIp);

    void insertLoginSession(int serverId,long userId,String sessionId,String hostIp);

    ServiceResult<Ticket> getTicket(int serverId, long userId, String hostIp);

    String test(String msg);
}
