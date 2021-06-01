package com.hcj.study.nettydemo.codec.protobuf;

import com.hcj.study.nettydemo.codec.protobuf.protobuf.PayCallbackReqProtoBuf;
import com.hcj.study.nettydemo.codec.protobuf.protobuf.PayCallbackRespProtoBuf;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * 基于netty的用protobuf编解码的服务端开发示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年06月01日 16:49
 */
@Slf4j
public class NettyProtoBufServer {
    private static int port = 8017;
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
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        //ProtobufVarint32FrameDecoder解码器用于半包处理
                        socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        //ProtobufDecoder解码器用于将数据解码成对应protobuf对象
                        socketChannel.pipeline().addLast(new ProtobufDecoder(PayCallbackReqProtoBuf.PayCallbackReq.getDefaultInstance()));
                        //ProtobufVarint32LengthFieldPrepender编码器对protobuf协议的的消息头上加上一个长度为32的整形字段,用于标志这个消息的长度
                        socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        //ProtobufEncoder编码器用于将消息编码成protobuf格式的字节数组
                        socketChannel.pipeline().addLast(new ProtobufEncoder());
                        socketChannel.pipeline().addLast(new PayCallbackReqHandler());
                    }
                })
                //连接处理器参数设置 SO_BACKLOG:服务端接受连接的队列长度,如果队列已满,客户端连接将被拒绝
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

    static class PayCallbackReqHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            PayCallbackReqProtoBuf.PayCallbackReq payCallbackReq = (PayCallbackReqProtoBuf.PayCallbackReq) msg;
            log.info("正在出货------->货品编号:[{}],数量:[{}]",payCallbackReq.getGoodId(),payCallbackReq.getCount());
            PayCallbackRespProtoBuf.PayCallbackResp payCallbackResp = PayCallbackRespProtoBuf.PayCallbackResp.newBuilder().setCode(0).setMessage("出货成功").build();
            ctx.writeAndFlush(payCallbackResp);
            log.info("出货成功............");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("server io error:{}",cause.getMessage());
            ctx.close();
        }
    }
}
