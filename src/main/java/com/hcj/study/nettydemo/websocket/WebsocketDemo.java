package com.hcj.study.nettydemo.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * 基于netty开发websocket服务端示例
 * 客户端用在线websocket连接来测试
 * 聊天室demo
 * @author 冰镇柠檬汁
 * @date 2021年06月04日 14:29
 */
@Slf4j
public class WebsocketDemo {
    private static int port = 8018;
    private static ServerBootstrap server;
    private static EventLoopGroup acceptGroup;
    private static EventLoopGroup workGroup;

    static {
        //创建用于轮询客户端连接的Reactor模型线程池,不指定大小时会默认根据系统cpu核数选择最大线程数
        acceptGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("server-connect-thread-pool"));
        //创建用于轮询已经连接的客户端请求的Reactor模型线程池
        workGroup = new NioEventLoopGroup();
        //创建服务端启动类
        server = new ServerBootstrap();
        //参数设置
        server.group(acceptGroup, workGroup)
                //指定服务端通道处理类
                .channel(NioServerSocketChannel.class)
                //设置IO处理器,服务于workGroup中的线程,用于处理已连接客户端的请求
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        //将请求和应答消息编解码为http消息
                        socketChannel.pipeline().addLast(new HttpServerCodec());
                        //将http消息的多个部分组合成一条完整的http消息
                        socketChannel.pipeline().addLast(new HttpObjectAggregator(64 * 1024));
                        //支持浏览器和服务端进行websocket通信
                        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                        //若要支持http请求的处理,可增加http请求处理器,这里测试通过uri直接升级为websocket请求
                        //通过uri来判断是否为socket请求,uri指定为"/",即ws://ip:port后不带任何路径
                        //客户端连接时ws://ip:port后若加其他路径会被视为http请求,而该示例中没有处理http请求,请求会阻塞
                        socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/"));
                        socketChannel.pipeline().addLast(new WebsocketServerHandler());
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public static void main(String[] args) {
        try {
            //绑定端口启动服务端并同步等待结果
            ChannelFuture future = server.bind(new InetSocketAddress("127.0.0.1", port)).sync();
            log.info("websocket server started:{}", future.channel().localAddress());
            //阻塞至服务端channel关闭后退出
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("服务器启动异常:{}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {
            //关闭线程池
            acceptGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            log.info("websocket server closed...");
        }
    }

    /**
     * 显示处理 TextWebSocketFrame,其他的会由 WebSocketServerProtocolHandler 自动处理
     */
    static class WebsocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        //存放聊天室中所有客户端连接
        private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) {
            Channel currentChannel = channelHandlerContext.channel();
            channels.forEach(channel -> {
                //把消息发给其他用户
                if (channel != currentChannel) {
                    channel.writeAndFlush(new TextWebSocketFrame("[" + currentChannel.remoteAddress() + "]:" + msg.text()));
                }
            });
        }

        /**
         * 每当服务端收到新的客户端连接时,客户端的Channel存入ChannelGroup列表中,并通知列表中的其他客户端Channel
         *
         * @param ctx
         */
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            Channel currentChannel = ctx.channel();
            channels.forEach(channel-> channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + currentChannel.remoteAddress() + " 加入")));
            channels.add(ctx.channel());
            log.info("Client:{}加入",currentChannel.remoteAddress());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            Channel currentChannel = ctx.channel();
            channels.forEach(channel-> channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + currentChannel.remoteAddress() + " 离开")));
            channels.remove(ctx.channel());
            log.info("Client:{}离开",currentChannel.remoteAddress());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("websocket server io error:{} client:[{}]", cause.getMessage(), ctx.channel().remoteAddress());
            ctx.close();
        }


    }
}
