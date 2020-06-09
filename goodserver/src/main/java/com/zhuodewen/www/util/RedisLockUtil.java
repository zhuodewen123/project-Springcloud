package com.zhuodewen.www.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    *  获得锁/判断加锁是否成功 (value值不重要,随便设置)
    *  key	    键名
    *  second	key失效的秒数
    */
    public boolean getLock(String key, long second) {
        Boolean isSuccess = false;
        try{
            isSuccess =redisTemplate.opsForValue().setIfAbsent(key,"value");
            if(isSuccess){
                //如果加锁成功,则设置超时时间
                redisTemplate.expire(key, second, TimeUnit.SECONDS);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /*
    *  释放锁
    *  key	要删除的键名
    */
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
