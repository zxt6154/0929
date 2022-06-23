package com.cqupt.annotation.component;

import cn.vcinema.pumpkin.activity.api.config.ActivityConfig;
import cn.vcinema.pumpkin.activity.api.singleton.RedisSingleton;
import cn.vcinema.tool.log.ActivityLogService;
import cn.vcinema.tool.pumpkin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Han ZC
 * @date 2020-06-09 15:49
 */
@Configuration
public class CacheBeanAutoCreator {

    private static final Logger logger = LoggerFactory.getLogger(CacheBeanAutoCreator.class);

    @Autowired
    private ActivityConfig activityConfig;

    @Autowired
    private RedisSingleton redisSingleton;

    @Bean
    public ActivityCache createActivityCache() {
        return new ActivityCache(redisSingleton.getRedis());
    }

    @Bean
    public UserCache createUserCache() {
        return new UserCache(redisSingleton.getRedis());
    }

    @Bean
    public IUSCache createIUSCache(){
        return new IUSCache(redisSingleton.getRedis());
    }

    @Bean
    public ActivityLogService createActivityLogService() {
        logger.info("初始化activity日志服务");
        return new ActivityLogService(activityConfig.getRunMode());
    }

    @Bean
    public RedisLockCache createLockCache(){
        return new RedisLockCache(redisSingleton.getRedis());
    }

    @Bean
    public SecurityCache createSecurityCache(){
        return new SecurityCache(redisSingleton.getRedis());
    }
}
