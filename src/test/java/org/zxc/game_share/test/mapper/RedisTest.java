package org.zxc.game_share.test.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;

    @Test
    public void contextLoad(){
//        redisTemplate.opsForValue(); // 操作字符串, 类似String
//        // 如
//        redisTemplate.opsForList(); // 操作 list
//        redisTemplate.opsForSet(); // 操作 set
//        redisTemplate.opsForHash(); // 操作hash
//        redisTemplate.opsForZSet(); // 操作
//        redisTemplate.opsForGeo(); // 操作Geo
//        redisTemplate.delete(key)  // 删除键

        ObjectMapper objectMapper = new ObjectMapper();

        redisTemplate.opsForValue().set("名字", "刘备");
        System.out.println(redisTemplate.opsForValue().get("名字"));
    }
}
