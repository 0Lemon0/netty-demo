package com.hcj.study.nettydemo.question.demo;

import cn.hutool.core.date.DateUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

import static com.hcj.study.nettydemo.base.constants.Constants.INVALID_ORDER_TIPS;
import static com.hcj.study.nettydemo.base.constants.Constants.VALID_ORDER;

/**
 * 服务端简易示例
 * 1.粘包示例:运行结果显示服务端发生了粘包,100次请求合成了n次(n<100)
 * 2.拆包示例:运行结果显示服务端读取请求数据时发生了拆包,1次请求拆成了2次
 * @author 冰镇柠檬汁
 * @date 2021年05月26日 15:22
 */
@Slf4j
public class Server {
    private static int port = 8014;
    private static ServerBootstrap server;
    private static EventLoopGroup acceptGroup;
    private static EventLoopGroup workGroup;

    static {
        acceptGroup = new NioEventLoopGroup(1,new DefaultThreadFactory("server-connect-thread-pool"));
        workGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(acceptGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new Handler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,100);
    }

    public static void main(String[] args) {
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

    static class Handler extends ChannelInboundHandlerAdapter{
        //记录客户端请求次数
        private int orderCount = 0;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf readBuf = (ByteBuf) msg;
            String order = readBuf.toString(CharsetUtil.UTF_8);
            //字符串截取操作只为去掉后面的换行符
            log.info("client order is:{}",order.substring(0,order.length()-System.getProperty("line.separator").length()));
            log.info("收到客户端请求次数:{}",++orderCount);
            String response = VALID_ORDER.equalsIgnoreCase(order) ? DateUtil.now() : INVALID_ORDER_TIPS + System.getProperty("line.separator");
            ByteBuf writeBuf = ctx.alloc().buffer(response.getBytes().length);
            writeBuf.writeBytes(response.getBytes());
            ctx.writeAndFlush(writeBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("server io error:{}",cause.getMessage());
            ctx.close();
        }
    }
}
