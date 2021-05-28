package com.hcj.study.nettydemo.question.demo;

import cn.hutool.core.net.NetUtil;
import com.hcj.study.nettydemo.base.constants.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

/**
 * 客户端简易示例
 * 运行结果显示客户端也发生了粘包,服务端响应了n次,但客户端只收到了m次响应(m<n)
 * @author 冰镇柠檬汁
 * @date 2021年05月26日 15:37
 */
@Slf4j
public class Client {
    private static int port = NetUtil.getUsableLocalPort();
    private static Bootstrap client;
    private static EventLoopGroup workGroup;

    static {
        workGroup = new NioEventLoopGroup();
        client = new Bootstrap();
        client.group(workGroup)
                .channel(NioSocketChannel.class)
                .localAddress("127.0.0.1",port)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new Handler());
                    }
                });
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            ChannelFuture channelFuture = client.connect("127.0.0.1",8014).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            log.info("client closed...");
        }
    }

    static class Handler extends ChannelInboundHandlerAdapter{
        private int responseCount = 0;
        //命令跟换行符
        private byte[] order = (Constants.VALID_ORDER + System.getProperty("line.separator")).getBytes(CharsetUtil.UTF_8);
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            log.info("连接服务端成功:{}", ctx.channel().localAddress());
            //粘包示例代码
            /*IntStream.range(0,100).forEach(i->{
                ByteBuf writeBuf = ctx.alloc().buffer(order.length);
                writeBuf.writeBytes(order);
                ctx.writeAndFlush(writeBuf);
            });*/
            //拆包示例代码
            StringBuffer order = new StringBuffer("这是一个");
            IntStream.range(0,100).forEach(i-> order.append("很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长"));
            order.append("的字符串");
            ByteBuf writeBuf = ctx.alloc().buffer(order.toString().length());
            writeBuf.writeBytes((order.toString() + System.getProperty("line.separator")).getBytes());
            ctx.writeAndFlush(writeBuf);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf readBuf = (ByteBuf) msg;
            log.info("response is:{} ",readBuf.toString(CharsetUtil.UTF_8));
            log.info("服务端响应次数:{}",++responseCount);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("client io error:{}",cause.getMessage());
            ctx.close();
        }
    }
}
