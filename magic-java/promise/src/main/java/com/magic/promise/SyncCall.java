package com.magic.promise;

/**
 * <p>
 *     同步执行
 * </p>
 */
public class SyncCall {

    public static void main(String[] args) throws Exception {

        String result = getIt();
        doThen(result);

    }

    private static String getIt() throws InterruptedException{
        Thread.sleep(3000L);
        System.out.println("经过一段漫长的等待，我执行了!");
        return "它就是: 微信";
    }

    private static void doThen(String result){
        System.out.println("来吧,让我来揭秘吧:\n" + result);
    }


}
