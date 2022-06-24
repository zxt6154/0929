package com.example.newmodule.services;

import com.example.newmodule.component.DistributeLock;
import com.example.newmodule.config.RedisOperate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ServiceTest {

    @Autowired
    ThreadPoolTaskExecutor asyncThread;

    @Autowired
    RedisOperate redisOperate;

    public void testDistributeLock() {
        testDistributeLock1();
        testDistributeLock2();
        testDistributeLock3();
        testDistributeLock4();
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//            for (int a = 0; a < 3; a++) {
//
//                int finalA = a;
//                executorService.submit(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("测试 -> " + finalA);
//                        distributeLockTest();
//                    }
//                });
//            }
//
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //@DistributeLock()
    public void distributeLockTest() {

//        redisOperate.hsetnx()
        System.out.println("distributeLockTest-------");
        int i = 3;
        int j = 4;
        System.out.println(j-i);
        int i1 = 9;
        int j1 = 2;
        System.out.println(j1-i1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    public void testDistributeLock1() {
        asyncThread.execute(new Runnable() {
            @Override
            public void run() {
//                int i1 = 9;
//                int j1 = 2;
//                System.out.println(j1-i1);
                distributeLockTest();
                System.out.println("当前线程");
                System.out.println(Thread.currentThread());
                System.out.println("testDistributeLock1()");
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

    }


    public void testDistributeLock2() {
       asyncThread.execute(new Runnable() {
           @Override
           public void run() {
//               try {
//                   Thread.sleep(100);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
               distributeLockTest();
               System.out.println("当前线程");
               System.out.println(Thread.currentThread());
//               int i = 3;
//               int j = 4;
//               System.out.println(j-i);
               System.out.println("testDistributeLock2");
           }
       });
    }
    public void testDistributeLock3() {
        asyncThread.execute(new Runnable() {
            @Override
            public void run() {
                distributeLockTest();
                System.out.println("当前线程");
                System.out.println(Thread.currentThread());
                System.out.println("testDistributeLock3");
//                try {
//                    Thread.sleep(900);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }


    public void testDistributeLock4() {
       asyncThread.execute(new Runnable() {
           @Override
           public void run() {
               distributeLockTest();
               System.out.println("当前线程");
               System.out.println(Thread.currentThread());
               System.out.println("testDistributeLock4");
           }
       });
    }
}
