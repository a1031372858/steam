package com.xu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.bean.Ticket;
import com.xu.bean.UserDetail;
import com.xu.bean.custom.ResponseWebDto;
import com.xu.controller.CertificateController;
import com.xu.mapper.UserDetailMapper;
import com.xu.service.TicketService;
import com.xu.service.dubboImpl.DubboCertServiceImpl;
import com.xu.utility.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class TicketServiceImpl implements TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public ResponseWebDto checkTicket(String ticketCode, String hostIp) {

        logger.info(DubboCertServiceImpl.class.getName()+" 入参[ticketCode:"+ticketCode+",hostIp:"+hostIp+"]");

        ResponseWebDto responseWebDto = new ResponseWebDto();
        if (StringUtils.isEmpty(ticketCode) || hostIp==null){
            responseWebDto.setStatus(0);
            responseWebDto.setMessage("失败");
            return responseWebDto;
        }

        //根据ticket和ip地址判断ticket是否有效
        Ticket checkTicket = null;
        try {
            checkTicket = (Ticket)redisUtil.get(ticketCode);
        }catch (Exception e){
            logger.error(e.toString());
        }

        if (null != checkTicket) {
            //redis删除ticket
            redisUtil.remove(ticketCode);
            UserDetail userDetail = userDetailMapper.selectUserByUserId(checkTicket.getUserId());


            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            String json = null;
            try {
                json = objectMapper.writeValueAsString(userDetail);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            responseWebDto.setStatus(1);
            responseWebDto.setResponseContent(json);
            responseWebDto.setMessage("成功");
        } else {
            responseWebDto.setStatus(0);
            responseWebDto.setMessage("失败");
        }

        return responseWebDto;
    }
}
