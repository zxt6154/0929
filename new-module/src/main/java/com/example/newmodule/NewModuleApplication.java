package com.example.newmodule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.newmodule.repository")
public class NewModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewModuleApplication.class, args);
    }

}
