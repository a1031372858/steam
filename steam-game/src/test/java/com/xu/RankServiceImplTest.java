package com.xu;

import com.xu.bean.RankCustomWebDto;
import com.xu.controller.GameController;
import com.xu.service.RankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameApplication.class)
public class RankServiceImplTest {


    @Autowired
    private RankService rankService;


    @Test
    public void selectRanks(){
        List<RankCustomWebDto> rankCustomWebDtos = rankService.selectRanks(0, 0, new Timestamp(1));
    }
}
