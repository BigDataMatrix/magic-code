package com.magic.promise;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JavaCall {

    public static void main(String[] args) throws Exception{

        ExecutorService engine = Executors.newSingleThreadScheduledExecutor();

        Future<String> resultFuture = engine.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000L);
                System.out.println("经过一段漫长的等待，我执行了!");
                return "它就是: 微信";
            }

        });

        doThen(resultFuture.get());

        new Scanner(System.in);

    }



    private static void doThen(String result){
        System.out.println("来吧,让我来揭秘吧:\n" + result);
    }

}
