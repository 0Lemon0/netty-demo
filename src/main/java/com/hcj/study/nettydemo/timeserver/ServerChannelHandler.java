package com.hcj.study.nettydemo.timeserver;

import cn.hutool.core.date.DateUtil;
import com.hcj.study.nettydemo.base.constants.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import static com.hcj.study.nettydemo.base.constants.Constants.INVALID_ORDER_TIPS;
import static com.hcj.study.nettydemo.base.constants.Constants.VALID_ORDER;

/**
 * 服务端读写IO处理器
 * PS::@Sharable 所有的客户端连接共享同一个Handler,若不加当客户端第二次连接时会报错
 * 也可以在客户端连接时通过new一个新的handler来处理
 * public class NettyChannelHandler extends ChannelInitializer<SocketChannel> {
 *     @Override
 *     protected void initChannel(SocketChannel sc) throws Exception {
 *         sc.pipeline().addLast(new ServerChannelHandler());
 *     }
 * @author 冰镇柠檬汁
 * @date 2021年05月19日 16:17
 */
@Sharable
@Slf4j
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当有新客户端连接时
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("收到客户端的连接:{}",ctx.channel().remoteAddress());
    }

    /**
     * 失去客户端连接
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("客户端已断开连接:{}",ctx.channel().remoteAddress());
        ctx.close();
    }

    /**
     * 当通道有数据可读时
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf readBuf = (ByteBuf) msg;
        String order = readBuf.toString(CharsetUtil.UTF_8);
        if(Constants.ORDER_CLOSE.equalsIgnoreCase(order)){
            log.info("client[{}] disconnected...",ctx.channel().remoteAddress());
            ctx.close();
            return;
        }
        log.info("client[{}] input order is {}", ctx.channel().remoteAddress(), order);
        String response = VALID_ORDER.equalsIgnoreCase(order) ? DateUtil.now() : INVALID_ORDER_TIPS;
        ByteBuf writeBuf = ctx.alloc().buffer(response.getBytes().length);
        //将服务端响应数据写入缓冲区
        writeBuf.writeBytes(response.getBytes());
        //发送数据并增加监听器,监听写操作,写操作完成,关闭通道
        ctx.channel().writeAndFlush(writeBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server io error:{}", cause.getMessage());
        //关闭通道
        ctx.close();
    }
}
