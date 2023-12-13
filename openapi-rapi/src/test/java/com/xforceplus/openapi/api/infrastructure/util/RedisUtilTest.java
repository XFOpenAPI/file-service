//package com.xforceplus.openapi.api.infrastructure.util;
//
//import com.xforceplus.openapi.api.infrastructure.common.constant.Constants;
//import org.junit.jupiter.api.Test;
//import org.redisson.api.RedissonClient;
//
//import java.awt.image.Kernel;
//import java.util.Date;
//import java.util.concurrent.ThreadPoolExecutor;
//
//public class RedisUtilTest {
//
//    @Test
//    public void testLock() throws InterruptedException {
//        ThreadPoolExecutor taskExecutorService = ExecutorUtil.getFixedThreadPoolExecutor(10, 10, "aggregateRecognitionVerifyImageTask");
//
//        RedissonClient testRedissonClient = RedisUtil.getTestRedissonClient();
//
//
//        for (int i = 0; i < 5; i++) {
//            //Thread.sleep(500);
//            taskExecutorService.submit(() -> {
//                Long taskId = 1717038666422943745L;
//                String lockNameKey = String.format(Constants.CHECK_TASK_FINISH_STATUS, taskId);
//                RedisDistributeLock redisDistributeLock = new RedisDistributeLock();
//                boolean isLockKey = redisDistributeLock.tryFairLock(lockNameKey, 2, 3);
//                if(!isLockKey){
//                    return;
//                }
//                try {
//                    System.out.println("key:"+ lockNameKey + " time:" + new Date());
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                boolean b = redisDistributeLock.releaseLock();
//
//                System.out.println(b);
//
//            });
//        }
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}