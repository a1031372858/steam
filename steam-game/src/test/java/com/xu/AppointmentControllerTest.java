package com.xu;

import com.xu.controller.AppointmentController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameApplication.class)
public class AppointmentControllerTest {

    @Autowired
    private AppointmentController appointmentController;

    @Test
    public void appointment(){
        String result = appointmentController.appointmentHour(11,1);
        Assert.assertTrue(true);
    }
}
