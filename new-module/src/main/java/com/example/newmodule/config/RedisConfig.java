package com.example.newmodule.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "spring.redis")
@Getter
@Setter
@NoArgsConstructor
public class RedisConfig {
    private String host;

    private int port;

    private int timeout;//秒

    private int database;
    //private String password;

    private HashMap<String, String> pool = new HashMap<>();
}
