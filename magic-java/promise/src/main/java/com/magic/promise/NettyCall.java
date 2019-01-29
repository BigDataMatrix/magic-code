package com.magic.promise;

import io.netty.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class NettyCall {

    public static void main(String[] args) throws Exception {

        long now = System.currentTimeMillis();
        EventExecutorGroup eventGroup = new DefaultEventExecutorGroup(4);
        Future resultFuture = eventGroup.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000L);
                System.out.println("经过一段漫长的等待，我执行了!");
                return "它就是: 微信";
            }
        });

        resultFuture.addListener(new FutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println("来吧,让我来揭秘吧:\n" + future.get());
            }
        });

        System.out.println("你尽管走起，我计算耗时: " + (System.currentTimeMillis() - now));

        new CountDownLatch(1).await();


    }



}
