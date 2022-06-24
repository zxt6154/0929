package com.example.newmodule;


import com.example.newmodule.config.JedisConfig;
import com.example.newmodule.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisTest {

    @Autowired
  private RedisUtil redisUtil;


    public void testJedis() {
        redisUtil.set("ceshi", "ceshi");
    }



}
