package com.xu;

import com.xu.bean.custom.ResponseWebDto;
import com.xu.controller.GameController;
import com.xu.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameApplication.class)
public class GameControllerTest {

    @Autowired
    private GameController gameController;

    @Test
    public void test2test(){
        String result = gameController.test2();
    }

    @Test
    public void rankTest(){
        long gameId=1;
        int grade =Integer.MAX_VALUE;
        Timestamp updateAt = Timestamp.valueOf(LocalDateTime.of(2018,1,1,0,0));

        ResponseWebDto dto = gameController.getGameRank(gameId,grade,updateAt);
    }
}
