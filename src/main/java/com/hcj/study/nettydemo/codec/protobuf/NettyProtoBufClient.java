package com.hcj.study.nettydemo.codec.protobuf;

import cn.hutool.core.net.NetUtil;
import com.hcj.study.nettydemo.codec.protobuf.protobuf.PayCallbackReqProtoBuf;
import com.hcj.study.nettydemo.codec.protobuf.protobuf.PayCallbackRespProtoBuf;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于netty的用protobuf编解码的客户端开发示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年06月01日 17:07
 */
@Slf4j
public class NettyProtoBufClient {
    private static int port = NetUtil.getUsableLocalPort();
    private static Bootstrap client;
    private static EventLoopGroup workGroup;

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
                        //ProtobufVarint32FrameDecoder解码器用于半包处理
                        socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        //ProtobufDecoder解码器用于将数据解码成对应protobuf对象
                        socketChannel.pipeline().addLast(new ProtobufDecoder(PayCallbackRespProtoBuf.PayCallbackResp.getDefaultInstance()));
                        //ProtobufVarint32LengthFieldPrepender编码器对protobuf协议的的消息头上加上一个长度为32的整形字段,用于标志这个消息的长度
                        socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        //ProtobufEncoder编码器用于将消息编码成protobuf格式的字节数组
                        socketChannel.pipeline().addLast(new ProtobufEncoder());
                        socketChannel.pipeline().addLast(new PayCallbackRespHandler());
                    }
                });
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            ChannelFuture channelFuture = client.connect("127.0.0.1",8017).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            log.info("client closed...");
        }
    }

    static class PayCallbackRespHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            log.info("连接服务端成功,开始发送请求");
            PayCallbackReqProtoBuf.PayCallbackReq payCallbackReq = PayCallbackReqProtoBuf.PayCallbackReq.newBuilder().setGoodId(100000).setCount(2).build();
            ctx.writeAndFlush(payCallbackReq);
            log.info("发送完毕.........");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            PayCallbackRespProtoBuf.PayCallbackResp payCallbackResp = (PayCallbackRespProtoBuf.PayCallbackResp) msg;
            log.info("收到服务端响应:code[{}],message:[{}]",payCallbackResp.getCode(),payCallbackResp.getMessage());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            log.error("client io error:{}",cause.getMessage());
            ctx.close();
        }
    }
}
