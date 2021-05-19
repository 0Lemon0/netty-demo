package com.hcj.study.nettydemo.base.aio;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.hcj.study.nettydemo.base.constants.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * aio客户端示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月18日 16:45
 */
@Slf4j
public class AioClientDemo {
    private static int port;
    private static AsynchronousSocketChannel socketChannel;
    private static boolean stop = false;

    static {
        try {
            port = NetUtil.getUsableLocalPort();
            socketChannel = AsynchronousSocketChannel.open();
            socketChannel.bind(new InetSocketAddress("127.0.0.1", port));
        } catch (IOException e) {
            log.error("客户端初始化异常:{}", e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AioClientDemo aioClientDemo = new AioClientDemo();
        //连接服务端
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8012), aioClientDemo, new ConnectHandler(socketChannel));
        while (!stop) {
            Thread.sleep(1000);
        }
    }

    /**
     * 请求处理
     */
    private static void request() {
        try {
            log.info("请输入指令:\t");
            byte[] order = new BufferedReader(new InputStreamReader(System.in)).readLine().getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(order.length);
            writeBuffer.put(order);
            writeBuffer.flip();
            socketChannel.write(writeBuffer, writeBuffer, new ClientWriteHandler(StrUtil.utf8Str(order),socketChannel));
        } catch (IOException e) {
            log.error("请求处理异常:{}", e.getMessage(), e);
        }
    }

    /****************************************************connect handler***********************************************************/
    /**
     * 连接回调处理器
     */
    static class ConnectHandler implements CompletionHandler<Void, AioClientDemo> {
        private final AsynchronousSocketChannel socketChannel;

        public ConnectHandler(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void completed(Void result, AioClientDemo attachment) {
            try {
                log.info("连接服务端成功:{}", socketChannel.getLocalAddress());
                request();
            } catch (IOException e) {
                log.error("连接处理异常:{}", e.getMessage(), e);
            }
        }

        @Override
        public void failed(Throwable exc, AioClientDemo attachment) {
            try {
                socketChannel.close();
                AioClientDemo.stop = true;
                log.error("连接失败:{}", exc.getMessage(), exc);
            } catch (IOException e) {
                log.error("channel close error:{}", e.getMessage(), e);
            }
        }
    }

    /****************************************************write handler***********************************************************/

    /**
     * 写回调处理器
     */
    static class ClientWriteHandler implements CompletionHandler<Integer, ByteBuffer> {
        private String order;
        private final AsynchronousSocketChannel socketChannel;

        public ClientWriteHandler(String order, AsynchronousSocketChannel socketChannel) {
            this.order = order;
            this.socketChannel = socketChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer writeBuffer) {
            if (writeBuffer.hasRemaining()) {
                socketChannel.write(writeBuffer, writeBuffer, this);
            } else if (Constants.ORDER_CLOSE.equalsIgnoreCase(order)) {
                try {
                    AioClientDemo.stop = true;
                    socketChannel.close();
                    log.info("client closed...");
                } catch (IOException e) {
                    log.error("channel close error:{}", e.getMessage(), e);
                }
            } else {
                //读服务端响应
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(readBuffer, readBuffer, new ClientReadHandler(socketChannel));
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                log.info("write handler io error:{}", exc.getMessage(), exc);
                socketChannel.close();
                AioClientDemo.stop = true;
            } catch (IOException e) {
                log.error("channel close error:{}", e.getMessage(), e);
            }
        }
    }

    /****************************************************write handler***********************************************************/

    /**
     * 写回调处理器
     */
    static class ClientReadHandler implements CompletionHandler<Integer, ByteBuffer> {
        private final AsynchronousSocketChannel socketChannel;

        public ClientReadHandler(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer readBuffer) {
            readBuffer.flip();
            byte[] response = new byte[readBuffer.remaining()];
            readBuffer.get(response);
            log.info("服务端响应:{}", StrUtil.utf8Str(response));
            request();
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                log.info("read handler io error:{}", exc.getMessage(), exc);
                socketChannel.close();
                AioClientDemo.stop = true;
            } catch (IOException e) {
                log.error("channel close error:{}", e.getMessage(), e);
            }
        }
    }
}
