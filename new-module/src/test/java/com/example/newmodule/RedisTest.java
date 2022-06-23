package com.example.newmodule;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {

        // redisTemplate.opsForValue().set("str","String字符串");
        redisTemplate.boundValueOps("str").set("String字符串");
        System.out.println("str："+redisTemplate.opsForValue().get("str"));

        //2)hash 散列
        redisTemplate.boundHashOps("hashtest").put("name","hash1");
        redisTemplate.boundHashOps("hashtest").put("age","16");
        //获取所有域
        Set set = redisTemplate.boundHashOps("hashtest").keys();
        System.out.println("hash散列的所有域:"+set);
        //获取所有值
        List list = redisTemplate.boundHashOps("hashtest").values();
        System.out.println("hash散列的所有域的值:"+list);

        //3)set集合
        redisTemplate.boundSetOps("s_key").add("a","b","c");
        set = redisTemplate.boundSetOps("s_key").members();
        System.out.println("set集合中的所有元素："+set);
        //4)list列表
        redisTemplate.boundListOps("l_key").leftPush("a");
        redisTemplate.boundListOps("l_key").leftPush("b");
        redisTemplate.boundListOps("l_key").leftPush("c");
        //获取全部元素
        list = redisTemplate.boundListOps("l_key").range(0,-1);
        System.out.println("list列表中的所有元素:"+list);

        //5)sorted set
        redisTemplate.boundZSetOps("sort_key").add("a",100);
        redisTemplate.boundZSetOps("sort_key").add("b",90);
        redisTemplate.boundZSetOps("sort_key").add("d",80);
        redisTemplate.boundZSetOps("sort_key").add("c",80);
        set = redisTemplate.boundZSetOps("sort_key").range(0,-1);
        System.out.println("sorted set有序集合中的所有元素："+set);
    }

}
