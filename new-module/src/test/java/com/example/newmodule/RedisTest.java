package com.example.newmodule;


import com.example.newmodule.config.RedisOperate;
import com.example.newmodule.services.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisOperate redisOperate;

     @Autowired
     private ServiceTest serviceTest;

    @Test
    public void testDistributeLock() {
        serviceTest.testDistributeLock();
    }



    public void testJedis() {
        redisOperate.set("ceshi", "ceshi");
    }

    @Test
    public void test2() {
        //            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("test1 -> " + Thread.currentThread().getName());
//                    taskTransComponent.updateTaskStateAndInfo(333, 1, 1);
//                }
//            }).start();
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("test2 -> " + Thread.currentThread().getName());
//                taskTransComponent.updateTaskStateAndInfo(333, 1, 1);
//            }
//        }).start();
//
//        try {
//            TimeUnit.SECONDS.sleep(8);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 3; i++) {
//
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("测试 -> ");
//                    taskTransComponent.updateTaskStateAndInfo(999, 1, 1);
//                }
//            });
//        }
//
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
