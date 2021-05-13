package com.hcj.study.nettydemo.bio;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * socket服务端示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年04月22日 15:46
 */
@Slf4j
public class SocketServerDemo {
    private static ServerSocket serverSocket;
    private static ThreadPoolExecutor pool;

    static{
        try {
            int port = 8010;
            serverSocket = new ServerSocket(port);
            log.info("socket server启动成功,端口:{}",port);
            pool = new ThreadPoolExecutor(4,4,30, TimeUnit.SECONDS,new SynchronousQueue<>(),
                    new ThreadFactoryBuilder().setNamePrefix("server-thread").build(),new ThreadPoolExecutor.AbortPolicy());
        } catch (IOException e) {
            log.error("socket server启动异常:{}",e.getMessage(),e);
        }
    }

    public static void main(String[] args) throws IOException {
        while (true){
            Socket socket = serverSocket.accept();
            log.info("收到来自客户端[{}]的连接",socket.getRemoteSocketAddress());
            try {
                pool.execute(new SocketServerThread(socket));
            } catch (RejectedExecutionException e) {
                //TODO 只是断开了服务端的socket,不会响应客户端,而客户端连接是成功的,当客户端通信时会异常终止
                //TODO PS:超出连接上限时,如何直接拒绝客户端连接(更优雅)
                log.warn("超出连接上限:{}",socket.getRemoteSocketAddress());
                socket.close();
            }
        }
    }
}
