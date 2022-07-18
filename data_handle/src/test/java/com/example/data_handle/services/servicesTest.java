package com.example.data_handle.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class servicesTest {

    @Autowired
    ExcelServiceImpl services;

    @Test
    public void test1(){
        File file = new File("/Users/alice/Documents/wokesheet.xlsx");
        System.out.println(file);
    }

}