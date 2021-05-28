package com.hcj.study.nettydemo.question.solution2;

import cn.hutool.core.net.NetUtil;
import com.hcj.study.nettydemo.base.constants.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

/**
 * 客户端解决粘包/拆包问题简易示例
 * 通过DelimiterBasedFrameDecoder和StringDecoder编码器来解决
 * @author 冰镇柠檬汁
 * @date 2021年05月26日 15:37
 */
@Slf4j
public class Client {
    private static int port = NetUtil.getUsableLocalPort();
    private static Bootstrap client;
    private static EventLoopGroup workGroup;
    private static final String SEPARATOR = "#";

    static {
        workGroup = new NioEventLoopGroup();
        client = new Bootstrap();
        client.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .localAddress("127.0.0.1",port)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        ByteBuf byteBuf = Unpooled.copiedBuffer("#".getBytes());
                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new Handler());
                    }
                });
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            ChannelFuture channelFuture = client.connect("127.0.0.1",8015).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            log.info("client closed...");
        }
    }

    static class Handler extends ChannelInboundHandlerAdapter{
        private int responseCount = 0;
        private byte[] order = (Constants.VALID_ORDER + SEPARATOR).getBytes(CharsetUtil.UTF_8);
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            log.info("连接服务端成功:{}", ctx.channel().localAddress());
            //粘包示例代码
            IntStream.range(0,100).forEach(i->{
                ByteBuf writeBuf = ctx.alloc().buffer(order.length);
                writeBuf.writeBytes(order);
                ctx.writeAndFlush(writeBuf);
            });
            //拆包示例代码
            /*StringBuffer order = new StringBuffer("这是一个");
            IntStream.range(0,100).forEach(i-> order.append("很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长"));
            order.append("的字符串");
            ByteBuf writeBuf = ctx.alloc().buffer(order.toString().length());
            writeBuf.writeBytes((order.toString() + System.getProperty("line.separator")).getBytes());
            ctx.writeAndFlush(writeBuf);*/
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            String response = (String) msg;
            log.info("response is:{} ",response);
            log.info("服务端响应次数:{}",++responseCount);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("client io error:{}",cause.getMessage());
            ctx.close();
        }
    }
}
