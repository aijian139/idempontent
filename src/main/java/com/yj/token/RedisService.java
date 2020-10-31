package com.yj.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    public boolean set(String key,Object value,long expireTime){
        boolean result = false;

        try {
            redisTemplate.opsForValue().set(key,value,expireTime, TimeUnit.SECONDS);
           result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 判断key 是否存在
    public boolean exists(String key){
        return redisTemplate.hasKey(key);
    }

    // 根据key删除值
    public boolean remove(String key){
        if(exists(key)) return redisTemplate.delete(key);
        return false;
    }


}
