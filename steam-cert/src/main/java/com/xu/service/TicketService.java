package com.xu.service;

import com.xu.bean.custom.ResponseWebDto;
import org.springframework.stereotype.Service;

@Service
public interface TicketService {

    ResponseWebDto checkTicket(String ticketCode, String hostIp);
}
