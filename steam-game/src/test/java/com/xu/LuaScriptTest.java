package com.xu;

import com.xu.utility.LuaScript;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameApplication.class)
public class LuaScriptTest {

    @Autowired
    private LuaScript luaScript;

    @Test
    public void test1(){
        Long result = luaScript.allowGetGold("gold","gold:userIdSet","1");
        System.out.println(result);
    }
}
