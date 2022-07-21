package com.example.newmodule.controller;

import com.example.newmodule.services.SkuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkuController {

    @Autowired
    SkuServiceImpl skuServiceImpl;

    @GetMapping(value = "text")
    public void getWidget() {
      skuServiceImpl.testRedisLock();
    }

}
