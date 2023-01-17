package com.xu.controller;

import com.xu.bean.Rank;
import com.xu.bean.RankCustomWebDto;
import com.xu.bean.custom.ResponseWebDto;
import com.xu.service.RankService;
import com.xu.utility.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RequestMapping("ranks")
@RestController
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private HttpServletRequest rq;
    @Autowired
    private HttpServletResponse rp;
    @Autowired
    private RankService rankService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("upload")
    public ResponseWebDto putGameGrade(Rank rank){
        ResponseWebDto responseWebDto = new ResponseWebDto();

        try {
            Rank result = rankService.insertRank(rank.getGameId(),rank.getUserId(),rank.getGrade());
            if(result!=null){
                responseWebDto.setStatus(HttpStatus.OK.value());
                responseWebDto.setMessage(HttpStatus.OK.getReasonPhrase());
                responseWebDto.setResponseContent(result);
            }else{
                responseWebDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                responseWebDto.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            }
        }catch (Exception e){
            responseWebDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseWebDto.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            System.out.println(e);
        }

        return responseWebDto;
    }


    @PostMapping("rank")
    public ResponseWebDto getGameRank(Long gameId, int grade, Timestamp dateTime){
        ResponseWebDto responseWebDto = new ResponseWebDto();

        try {
            List<RankCustomWebDto> rankCustomWebDtoList = rankService.selectRanks(gameId,grade, dateTime);
            if(rankCustomWebDtoList!=null){
                responseWebDto.setStatus(HttpStatus.OK.value());
                responseWebDto.setMessage(HttpStatus.OK.getReasonPhrase());
                responseWebDto.setResponseContent(rankCustomWebDtoList);
            }else{
                responseWebDto.setStatus(HttpStatus.OK.value());
                responseWebDto.setMessage("没有数据");
            }
        }catch (Exception e){
            responseWebDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseWebDto.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            System.out.println(e);
        }

        return responseWebDto;
    }

    @GetMapping("rankPage")
    public ResponseWebDto getGameRanks(int pageNum,int pageSize){
        ResponseWebDto responseWebDto = new ResponseWebDto();

        try {
            List<RankCustomWebDto> rankCustomWebDtoList = rankService.selectPageRanks(pageNum,pageSize);
            if(rankCustomWebDtoList!=null){
                responseWebDto.setStatus(HttpStatus.OK.value());
                responseWebDto.setMessage(HttpStatus.OK.getReasonPhrase());
                responseWebDto.setResponseContent(rankCustomWebDtoList);
            }else{
                responseWebDto.setStatus(HttpStatus.OK.value());
                responseWebDto.setMessage("没有数据");
            }
        }catch (Exception e){
            responseWebDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseWebDto.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            System.out.println(e);
        }

        return responseWebDto;
    }

    @ApiOperation("测试")
    @PostMapping("test")
    public String test2() {
        redisUtil.setNX("test2","1",10L, TimeUnit.SECONDS);
        return "test2";
    }

}
