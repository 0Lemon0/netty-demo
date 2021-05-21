package com.hcj.study.nettydemo.timeserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * 基于netty实现的timeserver
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月19日 15:42
 */
@Component
@Slf4j
public class TimeServer implements CommandLineRunner {
    private static int port = 8013;
    private static ServerBootstrap server;
    private static EventLoopGroup acceptGroup;
    private static EventLoopGroup workGroup;

    static {
        //创建用于轮询客户端连接的Reactor模型线程池,不指定大小时会默认根据系统cpu核数选择最大线程数
        acceptGroup = new NioEventLoopGroup(1,new DefaultThreadFactory("server-connect-thread-pool"));
        //创建用于轮询已经连接的客户端请求的Reactor模型线程池
        workGroup = new NioEventLoopGroup();
        //创建服务端启动类
        server = new ServerBootstrap();
        //参数设置
        server.group(acceptGroup,workGroup)
                //指定服务端通道处理类
                .channel(NioServerSocketChannel.class)
                //设置IO处理器,服务于workGroup中的线程,用于处理已连接客户端的请求
                .childHandler(new ServerChannelHandler())
                //连接处理器参数设置 SO_BACKLOG:服务端接受连接的队列长度,如果队列已满,客户端连接将被拒绝
                .option(ChannelOption.SO_BACKLOG,100)
                //IO处理器参数设置,关闭延迟发送
                .childOption(ChannelOption.TCP_NODELAY,true);
    }

    @Override
    public void run(String... args) {
        try {
            //绑定端口启动服务端并同步等待结果
            ChannelFuture future = server.bind(new InetSocketAddress("127.0.0.1",port)).sync();
            log.info("server started:{}",future.channel().localAddress());
            //阻塞至服务端channel关闭后退出
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("服务器启动异常:{}",e.getMessage(),e);
            Thread.currentThread().interrupt();
        } finally {
            //关闭线程池
            acceptGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            log.info("server closed...");
        }
    }
}
