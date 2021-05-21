package com.hcj.study.nettydemo.timeserver;

import cn.hutool.core.net.NetUtil;
import com.hcj.study.nettydemo.base.constants.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件描述
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月20日 15:19
 */
@Slf4j
public class ClientDemo {
    private static int port;
    private static Bootstrap client;
    private static EventLoopGroup workGroup;

    static {
        port = NetUtil.getUsableLocalPort();
        //客户端只需要IO处理线程池
        workGroup = new NioEventLoopGroup(1,new DefaultThreadFactory("client-reactor-thread-pool"));
        //创建客户端启动类
        client = new Bootstrap();
        client.group(workGroup)
                //指定客户端通道处理类
                .channel(NioSocketChannel.class)
                //绑定本地端口
                .localAddress("127.0.0.1",port)
                //关闭延迟发送,禁用nagle算法
                .option(ChannelOption.TCP_NODELAY,true)
                //指定IO处理器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            //启动客户端,选择同步模式,等待客户端连接成功
            ChannelFuture channelFuture = client.connect("127.0.0.1",8013).sync();
            Channel channel = channelFuture.channel();
            log.info("连接服务端成功:{}", channel.localAddress());
            log.info("请输入指令:\t");
            String order = new BufferedReader(new InputStreamReader(System.in)).readLine();
            ByteBuf writeBuf = channel.alloc().buffer(order.getBytes().length);
            writeBuf.writeBytes(order.getBytes(CharsetUtil.UTF_8));
            channel.writeAndFlush(writeBuf).addListener(future -> {
                if(order.equalsIgnoreCase(Constants.ORDER_CLOSE)){
                    channel.close();
                }
            });
            //阻塞至channel关闭(服务端也可关闭)
            channel.closeFuture().sync();
        } catch (IOException e) {
            log.error("client request error:{}",e.getMessage(),e);
        } finally {
            workGroup.shutdownGracefully();
            log.info("client closed...");
        }
    }
}
