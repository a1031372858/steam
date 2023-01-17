package com.xu.utility;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @SuppressWarnings("unchecked")
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean setNX(final String key, Object value){
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result=operations.setIfAbsent(key,value);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean setNX(final String key, Object value, Long expireTime, TimeUnit timeUnit){
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result=operations.setIfAbsent(key,value,expireTime,timeUnit);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 自增，没有key的时候设置为1。
     * @param key
     * @return
     */
    public long increment(final String key){
        long result =1;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            if(exists(key)){
                result=operations.increment(key);
            }else{
                operations.set(key, 1);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long decrement(final String key){
        long result =1;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            if(exists(key)){
                result=operations.decrement(key);
            }else{
                operations.set(key, 1);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void rPush(final String key, Object value){
        redisTemplate.opsForList().rightPush(key,value);
    }

    public void lPush(final String key, Object value){
        redisTemplate.opsForList().leftPush(key,value);
    }

    public Object lPop(final String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    public Object rPop(final String key){
        return redisTemplate.opsForList().rightPop(key);
    }


    public long size(final String key){
        return redisTemplate.opsForList().size(key);
    }

    public boolean del(final String key) {
        boolean result = false;
        try {
            result = redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
