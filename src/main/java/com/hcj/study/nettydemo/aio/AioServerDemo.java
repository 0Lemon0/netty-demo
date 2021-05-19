package com.hcj.study.nettydemo.aio;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.hcj.study.nettydemo.constants.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import static com.hcj.study.nettydemo.constants.Constants.INVALID_ORDER_TIPS;
import static com.hcj.study.nettydemo.constants.Constants.VALID_ORDER;

/**
 * aio服务端示例
 * PS:所有的回调JVM底层都通过线程池来处理
 * @author 冰镇柠檬汁
 * @date 2021年05月18日 14:38
 */
@Slf4j
public class AioServerDemo {
    private static int port = 8012;
    private static AsynchronousServerSocketChannel serverSocketChannel;
    private static boolean stop = false;

    static {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", port));
            log.info("socket server启动成功,端口:{}", port);
        } catch (IOException e) {
            log.error("服务启动异常:{}", e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AioServerDemo demo = new AioServerDemo();
        //accept()方法会启动一个独立线程来异步处理客户端连接
        serverSocketChannel.accept(demo, new AcceptHandler());
        while (!stop) {
            //阻塞主线程,避免主线程退出导致回调线程退出
            Thread.sleep(30000);
        }
    }

    /****************************************************accept handler***********************************************************/

    /**
     * 连接回调处理器
     */
    static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AioServerDemo> {

        /**
         * 每有一个新的客户端连接成功,会回调该方法继续监听其他客户端连接
         *
         * @param asynchronousSocketChannel
         * @param attachment
         */
        @Override
        public void completed(AsynchronousSocketChannel asynchronousSocketChannel, AioServerDemo attachment) {
            try {
                log.info("收到来自客户端[{}]的连接",asynchronousSocketChannel.getRemoteAddress());
                //再启动一个新的线程异步接收其他客户端连接,形成循环
                AioServerDemo.serverSocketChannel.accept(attachment, this);
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //异步读取客户端请求
                asynchronousSocketChannel.read(readBuffer, readBuffer, new ServerReadHandler(asynchronousSocketChannel));
            } catch (IOException e) {
                log.error("accept io error:{}",e.getMessage(),e);
            }
        }

        @Override
        public void failed(Throwable exc, AioServerDemo attachment) {
            log.error("客户端连接异常:{}", exc.getMessage(), exc);
        }
    }

    /****************************************************read handler***********************************************************/

    /**
     * 异步读回调处理器
     */
    static class ServerReadHandler implements CompletionHandler<Integer, ByteBuffer> {
        private AsynchronousSocketChannel asynchronousSocketChannel;

        public ServerReadHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
            this.asynchronousSocketChannel = asynchronousSocketChannel;
        }

        /**
         * 当有客户端请求,会回调该方法
         *
         * @param result
         * @param readBuffer
         */
        @Override
        public void completed(Integer result, ByteBuffer readBuffer) {
            try {
                readBuffer.flip();
                byte[] body = new byte[readBuffer.remaining()];
                readBuffer.get(body);
                String order = StrUtil.utf8Str(body);
                log.info("[{}]input order is {}", asynchronousSocketChannel.getRemoteAddress(),order);
                if(order.equalsIgnoreCase(Constants.ORDER_CLOSE)){
                    //关闭客户端连接
                    log.info("client[{}] disconnected...",asynchronousSocketChannel.getRemoteAddress());
                    asynchronousSocketChannel.close();
                    return;
                }
                String response = VALID_ORDER.equalsIgnoreCase(order) ? DateUtil.now() : INVALID_ORDER_TIPS;
                response(response);
            } catch (IOException e) {
                log.error("read client request error:{}",e.getMessage(),e);
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                log.info("read handler io error:{}",exc.getMessage(),exc);
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                log.error("channel close error:{}",e.getMessage(),e);
            }
        }

        /**
         * 服务端响应
         * @param response
         */
        private void response(String response){
            byte[] body = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(body.length);
            writeBuffer.put(body);
            writeBuffer.flip();
            //异步写数据
            asynchronousSocketChannel.write(writeBuffer, writeBuffer, new ServerWriteHandler(asynchronousSocketChannel));
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //再异步读取客户端请求,形成循环
            asynchronousSocketChannel.read(readBuffer, readBuffer, new ServerReadHandler(asynchronousSocketChannel));
        }
    }

    /****************************************************write handler***********************************************************/

    /**
     * 异步写回调处理器
     */
    static class ServerWriteHandler implements CompletionHandler<Integer, ByteBuffer>{
        private AsynchronousSocketChannel asynchronousSocketChannel;

        public ServerWriteHandler(AsynchronousSocketChannel asynchronousSocketChannel) {
            this.asynchronousSocketChannel = asynchronousSocketChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer writeBuffer) {
            //如果没有发送完成,继续发送
            if(writeBuffer.hasRemaining()){
                asynchronousSocketChannel.write(writeBuffer,writeBuffer,this);
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                log.info("write handler io error:{}",exc.getMessage(),exc);
                asynchronousSocketChannel.close();
            } catch (IOException e) {
                log.error("channel close error:{}",e.getMessage(),e);
            }
        }
    }
}
