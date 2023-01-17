package com.xu.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class LuaScript {
    @Autowired
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> script;

    private DefaultRedisScript<Long> appointmentScript;

    @PostConstruct
    public void init(){
        script = new DefaultRedisScript<Long>();
        //返回值为Long
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/test.lua")));


        appointmentScript = new DefaultRedisScript<Long>();
        appointmentScript.setResultType(Long.class);
        appointmentScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/appointment.lua")));
    }

    public Long allowAppointment(String appointmentAmountKey,String userIdSetKey,String userId) {
        List<String> keys = new ArrayList<>();
        keys.add(appointmentAmountKey);
        keys.add(userIdSetKey);
        keys.add(userId);

        return (Long) redisTemplate.execute(appointmentScript, keys);
    }

    public Long allowGetGold(String goldKey,String setKey,String userId) {
        List<String> keys = new ArrayList<>();
        keys.add(goldKey);
        keys.add(setKey);
        keys.add(userId);

        //script:lua脚本
        //KEYS[1] KEYS[2]，是要操作的键，可以指定多个，在lua脚本中通过KEYS[1], KEYS[2]获取
        //ARGV[1] ARGV[2]，参数，在lua脚本中通过ARGV[1], ARGV[2]获取

        return (Long) redisTemplate.execute(script, keys);
    }

}
