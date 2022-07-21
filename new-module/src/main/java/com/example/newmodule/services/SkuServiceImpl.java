package com.example.newmodule.services;

import com.example.newmodule.annotaions.DistributeLock;
import com.example.newmodule.repository.entity.Sku;
import com.example.newmodule.repository.mapper.SkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class SkuServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(SkuServiceImpl.class);

    private final static String KEY = "REDIS_DISTRIBUTE_LOCK";

    @Value("${server.port}")
    String serverPort;

    @Autowired
    SkuMapper skuMapper;

    @RequestMapping("text")
    @DistributeLock(key = KEY, value = "6154")
    public void testRedisLock() {
        Sku sku = skuMapper.selectByPrimaryKey(1);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //logger.error("sku -> {}", sku);
        logger.error("serverPort -> {}", serverPort);
        if(sku.getGoodsNum() > 0){
            Sku skuEntity = new Sku();
            BeanUtils.copyProperties(sku, skuEntity);
            // skuEntity.setGoodsNum(sku.getGoodsNum() - 1);
            skuMapper.updateByPrimaryKey(skuEntity);
        }
        Sku skuAfter = skuMapper.selectByPrimaryKey(1);
        logger.error("skuAfter -> {}", skuAfter);
    }
}
