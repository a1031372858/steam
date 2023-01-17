package com.xu;

import com.xu.controller.TestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameApplication.class)
public class TestControllerTest {

    @Autowired
    private TestController testController;


    @Test
    public void load(){
        String result = testController.test2();
        System.out.println(result);
    }
}
