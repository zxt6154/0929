package com.example.newmodule.services;

import com.example.newmodule.config.RedisOperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author alice
 */
@Service
public class RedisLockAnnotationTest {

    final ThreadPoolTaskExecutor asyncThread;

    final RedisOperate redisOperate;


    public RedisLockAnnotationTest(ThreadPoolTaskExecutor asyncThread, RedisOperate redisOperate) {
        this.asyncThread = asyncThread;
        this.redisOperate = redisOperate;
    }



}
