package com.hcj.study.nettydemo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Random;

/**
 * 客户端程序
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月14日 11:06
 */
@Slf4j
public class ClientDemo {
    private static final int port = 8011;

    public static void main(String[] args) {
        //启动客户端轮询线程
        try {
            new Thread(new ClientReactorThread(),"nio-client-reactor-" + new Random().nextInt(10)).start();
        } catch (IOException e) {
            log.error("客户端程序启动异常:{}",e.getMessage(),e);
        }
    }
}
