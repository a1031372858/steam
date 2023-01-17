package com.xu.service;


import com.xu.bean.custom.ResponseWebDto;

public interface DubboCertService {

    ResponseWebDto checkTicket(String ticketCode, String hostIp);

    String test();
}
