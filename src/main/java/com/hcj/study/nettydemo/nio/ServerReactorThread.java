package com.hcj.study.nettydemo.nio;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务端多路复用器轮询线程
 * 一次请求=一个线程
 * @author 冰镇柠檬汁
 * @date 2021年05月14日 11:31
 */
@Slf4j
public class ServerReactorThread implements Runnable {
    private ServerSocketChannel acceptChannel;
    private Selector selector;
    private ThreadPoolExecutor pool;
    private int port;
    private volatile boolean stop = false;

    public ServerReactorThread(int port) throws IOException {
        this.port = port;
        acceptChannel = ServerSocketChannel.open();
        acceptChannel.socket().bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), this.port));
        //设置channel为非阻塞模式
        acceptChannel.configureBlocking(false);
        //创建多路复用器
        selector = Selector.open();
        //注册通道,监听accept事件
        acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
        //初始化服务端处理线程池,最大可同时处理4个客户端的请求
        pool = new ThreadPoolExecutor(4, 4, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500),
                new ThreadFactoryBuilder().setNamePrefix("nio-server-thread").build(), new ThreadPoolExecutor.AbortPolicy());
        log.info("socket server启动成功,端口:{}", this.port);
    }

    @Override
    public void run() {
        while (!stop) {
            SelectionKey key;
            try {
                //开始Selector轮询,selector每搁1s被唤醒一次,select()方法返回新增的就绪事件数
                //当处理过的key没有被移除时会存在select()方法返回0但是selectedKeys()方法有返回值的情况
                selector.select(1000);
                //当有就绪状态的channel时获取该通道的selectionKey集合
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                //PS:key处理完后需删除,否则selectedKeys()会一直返回处理过的key
                //PS:使用迭代器处理,避免删除元素时的并发修改异常
                Iterator<SelectionKey> it = selectionKeySet.iterator();
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    handle(key);
                }
            } catch (IOException e) {
                log.error("selector监听处理异常:{}", e.getMessage(), e);
            }
        }
        //服务端线程停止,关闭Selector
        //Selector关闭后，注册在上面的channel会自动关闭
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                log.error("resources close error:{}", e.getMessage(), e);
            }
        }
        pool.shutdown();
        log.info("server reactor thread stopped...");
    }

    /**
     * 处理客户端请求
     *
     * @param key
     */
    private void handle(SelectionKey key) throws IOException {
        if (!key.isValid()) {
            return;
        }
        //为连接就绪事件时
        if (key.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            //为每一个客户端连接创建一个socketChannel并建立TCP连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            //设置channel为非阻塞
            socketChannel.configureBlocking(false);
            //为该客户端channel注册读事件
            socketChannel.register(this.selector, SelectionKey.OP_READ);
            log.info("收到来自客户端[{}]的连接", socketChannel.socket().getRemoteSocketAddress());
            return;
        }
        try {
            //TODO 客户端掉线时(非主动关闭)selector.selectedKeys()会一直有返回key,但无法处理(连接已断开),如何处理这种情况
            //为读就绪事件时
            if (key.isReadable()) {
                //用线程池可同时处理多个客户端请求
                pool.execute(new ServerHandler(key));
            }
        } catch (RejectedExecutionException e) {
            log.error("服务端请求处理队列已满:{}", e.getMessage(), e);
        }
    }
}
