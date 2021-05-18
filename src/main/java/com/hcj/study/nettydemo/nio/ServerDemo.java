package com.hcj.study.nettydemo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * NIO示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月14日 11:06
 */
@Slf4j
public class ServerDemo {
    private static final int port = 8011;

    public static void main(String[] args) throws IOException {
        //启动服务端轮询线程
        new Thread(new ServerReactorThread(port),"nio-server-reactor").start();
    }
}
