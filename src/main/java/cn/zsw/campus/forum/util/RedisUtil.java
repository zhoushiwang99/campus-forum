package cn.zsw.campus.forum.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zsw
 * @date 2021/5/10 17:28
 */
@Component
public class RedisUtil {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public void setString(String key, String value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }

    public void setString(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

}
