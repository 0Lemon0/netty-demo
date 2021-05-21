package com.hcj.study.nettydemo.timeserver;

import com.hcj.study.nettydemo.base.constants.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 客户端IO处理器
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月20日 15:29
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 收到服务端响应
     * 响应消息末尾需有结束标志\n 否则会进入channelReadComplete方法
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf readBuf = (ByteBuf) msg;
            log.info("服务端响应:{}", readBuf.toString(CharsetUtil.UTF_8));
            log.info("请输入指令:\t");
            String order = new BufferedReader(new InputStreamReader(System.in)).readLine();
            ByteBuf writeBuf = ctx.alloc().buffer(order.getBytes().length);
            writeBuf.writeBytes(order.getBytes(CharsetUtil.UTF_8));
            ctx.channel().writeAndFlush(writeBuf).addListener(future -> {
                if(order.equalsIgnoreCase(Constants.ORDER_CLOSE)){
                    ctx.channel().close();
                }
            });
        } catch (IOException e) {
            log.error("输入指令读取异常:{}",e.getMessage(),e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("client io error:{}", cause.getMessage());
        //关闭通道
        ctx.close();
    }
}
