package com.example.newmodule;

import com.example.newmodule.services.ServicesImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class servicesTest {

    @Autowired
    ServicesImpl services;

    @Test
    public void test1(){
        //File file = new File("/Users/alice/Documents/wokesheet.xlsx");
        //services.test();
    }

}