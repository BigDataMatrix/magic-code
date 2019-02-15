package com.tiny;

import com.tiny.service.HelloService;
import com.tiny.service.HelloServiceImpl;

public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}
