package com.magic.promise;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class GuavaCall {

    public static void main(String[] args) throws Exception {

        long now = System.currentTimeMillis();

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());

        ListenableFuture listenableFuture = listeningExecutorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000L);
                System.out.println("经过一段漫长的等待，我执行了!");
                return "它就是: 微信";
            }

        });

        Futures.addCallback(listenableFuture, new FutureCallback<String>() {

            @Override
            public void onSuccess(String s) {
                System.out.println("来吧,让我来揭秘吧:\n" + s);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("出现了未可知的异常");
            }
        });

        new CountDownLatch(1).await();


    }


}
