package com.example.newmodule.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@Slf4j
public class JedisConfig {

//    @Autowired
//    private RedisConfig redisConfig;
   @Value("${spring.redis.pool.max-active}")
   private int maxActive;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;



    @Bean
    public JedisPoolConfig jedisPoolConfig () {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMinIdle(minIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
            return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        log.info("------------创建jedis连接池-------------------");
        JedisPool jedisPool = new JedisPool();
        if(StringUtils.isNotEmpty(password)) {
            return new JedisPool(jedisPoolConfig, host, port, timeout, password);
        }
        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }


//    @Bean
//    public JedisPool JedisFactory() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal(Integer.valueOf(redisConfig.getPool().get("max-active")));
//        poolConfig.setMaxIdle(Integer.valueOf(redisConfig.getPool().get("max-idle")));
//        poolConfig.setMaxWaitMillis(Integer.valueOf(redisConfig.getPool().get("max-wait")) * 1000);  //注意单位是ms，要转换单位
//        poolConfig.setMinIdle(Integer.valueOf(redisConfig.getPool().get("min-idle")));
//        return new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
//                redisConfig.getTimeout()*1000, null,redisConfig.getDatabase());
//    }



}
