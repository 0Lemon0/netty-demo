package com.hcj.study.nettydemo.base.nio;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hcj.study.nettydemo.base.constants.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import static com.hcj.study.nettydemo.base.constants.Constants.INVALID_ORDER_TIPS;
import static com.hcj.study.nettydemo.base.constants.Constants.VALID_ORDER;

/**
 * 服务端处理线程
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月14日 16:40
 */
@Slf4j
public class ServerHandler implements Runnable {
    private final SelectionKey key;

    public ServerHandler(SelectionKey key) {
        this.key = key;
    }

    /**
     * 处理客户端请求
     */
    @Override
    public void run() {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        String order = readOrder(socketChannel);
        if (order != null) {
            try {
                log.info("[{}]input order is {}", socketChannel.getRemoteAddress(),order);
                //客户端发送关闭命令
                if(order.equalsIgnoreCase(Constants.ORDER_CLOSE)){
                    key.cancel();
                    key.channel().close();
                    return;
                }
            } catch (IOException e) {
                log.error("服务端处理异常:{}",e.getMessage(),e);
            }
            String response = VALID_ORDER.equalsIgnoreCase(order) ? DateUtil.now() : INVALID_ORDER_TIPS;
            response(socketChannel, response);
            //也可用hutool封装好的创建Buffer的方法
            //socketChannel.write(BufferUtil.createUtf8(response));
        }
    }

    /**
     * 读取客户端请求命令
     *
     * @param socketChannel
     * @return
     */
    private String readOrder(SocketChannel socketChannel) {
        try {
            //创建读Buffer
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //从channel读取数据到缓冲区,由于创建channel时设置为异步非阻塞,所以read操作为非阻塞
            int readByte = socketChannel.read(readBuffer);
            //通过读到的字节数判断是否有数据传输
            if (readByte > 0) {
                //从起始位置开始读,调整读/写指针指到缓冲区头部
                readBuffer.flip();
                //创建byte数组用于存放读取的数据,长度为readBuffer长度
                byte[] bytes = new byte[readBuffer.remaining()];
                //读取数据到byte数组
                readBuffer.get(bytes);
                //转换得到客户端请求命令
                return StrUtil.utf8Str(bytes);
            } else if (readByte < 0) {
                //radByte为-1时表示客户端链路已经关闭,需关闭channel,释放资源
                //停止selectionKey的监控
                key.cancel();
                //关闭channel
                socketChannel.close();
            }
        } catch (IOException e) {
            log.error("socket read error:{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 客户端响应
     *
     * @param socketChannel
     * @param response
     * @throws IOException
     */
    private void response(SocketChannel socketChannel, String response) {
        try {
            //将响应内容转成字节数组
            byte[] responseBytes = response.getBytes();
            //创建写Buffer
            ByteBuffer writeBuffer = ByteBuffer.allocate(responseBytes.length);
            //将响应内容字节写入缓冲区
            writeBuffer.put(responseBytes);
            writeBuffer.flip();
            //异步发送服务端响应
            //TODO 由于是异步非阻塞,并不能保证一次性发送所有数据字节,可能存在写半包的问题
            socketChannel.write(writeBuffer);
        } catch (IOException e) {
            log.error("socket write error:{}", e.getMessage(), e);
        }
    }
}
