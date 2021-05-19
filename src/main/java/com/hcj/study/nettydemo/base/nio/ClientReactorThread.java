package com.hcj.study.nettydemo.base.nio;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.hcj.study.nettydemo.base.constants.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端多路复用器轮询线程
 * 一个线程=一个客户端
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月14日 11:31
 */
@Slf4j
public class ClientReactorThread implements Runnable {
    private SocketChannel connectSocketChannel;
    private Selector selector;
    private int port = NetUtil.getUsableLocalPort();
    private volatile boolean stop = false;

    public ClientReactorThread() throws IOException {
        selector = Selector.open();
        connectSocketChannel = SocketChannel.open();
        connectSocketChannel.bind(new InetSocketAddress("127.0.0.1", port));
        connectSocketChannel.configureBlocking(false);
        //初始化客户端时,异步连接服务端
        if (connectSocketChannel.connect(new InetSocketAddress("127.0.0.1", 8011))) {
            log.info("客户端初始化成功:{}", connectSocketChannel.getLocalAddress());
            //监听读事件
            connectSocketChannel.register(selector, SelectionKey.OP_READ);
            doRequest(connectSocketChannel);
            return;
        }
        //当还没收到服务端TCP连接应答时,监听连接事件,等服务端回应ACK后再建立连接
        connectSocketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    @Override
    public void run() {
        while (!stop) {
            SelectionKey key;
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //PS:key处理完后需删除,否则selectedKeys()会一直返回处理过的key
                //PS:使用迭代器处理,避免删除元素时的并发修改异常
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    handle(key);
                }
            } catch (IOException e) {
                log.error("selector监听处理异常:{}", e.getMessage(), e);
            }
        }
        //客户端线程停止,关闭Selector
        //Selector关闭后，注册在上面的channel会自动关闭
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                log.error("resources close error:{}", e.getMessage(), e);
            }
        }
        log.info("client reactor thread stopped...");
    }

    /**
     * 客户端处理
     *
     * @param key
     */
    private void handle(SelectionKey key) {
        if (!key.isValid()) {
            return;
        }
        //TODO 服务端掉线时(非主动关闭)selector.selectedKeys()会一直有返回key,但无法处理(连接已断开),如何处理这种情况
        SocketChannel socketChannel = (SocketChannel) key.channel();
        //连接就绪
        if (key.isConnectable()) {
            connect(socketChannel);
            return;
        }
        //收到服务端响应
        if (key.isReadable()) {
            showResponse(key, socketChannel);
        }
    }

    /**
     * 连接服务端
     */
    private void connect(SocketChannel socketChannel) {
        try {
            //连接成功
            if (socketChannel.finishConnect()) {
                log.info("连接服务端成功:{}", socketChannel.getLocalAddress());
                //监听读事件
                socketChannel.register(selector, SelectionKey.OP_READ);
                doRequest(socketChannel);
                return;
            }
        } catch (IOException e) {
            log.error("服务端连接异常:{}", e.getMessage(), e);
        }
        //连接失败,退出
        stop = true;
    }

    /**
     * 给服务端发送指令
     */
    private void doRequest(SocketChannel socketChannel) {
        log.info("请输入指令:\t");
        try {
            byte[] order = new BufferedReader(new InputStreamReader(System.in)).readLine().getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(order.length);
            writeBuffer.put(order);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
            if (Constants.ORDER_CLOSE.equalsIgnoreCase(StrUtil.utf8Str(order))) {
                selector.close();
                //关闭客户端
                stop = true;
            }
        } catch (IOException e) {
            log.error("请求异常:{}", e.getMessage(), e);
        }
    }

    /**
     * 显示服务端响应结果
     *
     * @param socketChannel
     */
    private void showResponse(SelectionKey key, SocketChannel socketChannel) {
        try {
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            //从channel读取数据到缓冲区,由于创建channel时设置为异步非阻塞,所以read操作为非阻塞
            int readByte = socketChannel.read(readBuffer);
            if (readByte > 0) {
                //从起始位置开始读,调整读/写指针指到缓冲区头部
                readBuffer.flip();
                //创建byte数组用于存放读取的数据,长度为byteBuffer长度
                byte[] bytes = new byte[readBuffer.remaining()];
                //读取数据到byte数组
                readBuffer.get(bytes);
                //转换得到客户端请求命令
                log.info("服务端响应:{}", StrUtil.utf8Str(bytes));
                doRequest(socketChannel);
            } else if (readByte < 0) {
                //radByte为-1时表示客户端链路已经关闭,需关闭channel,释放资源
                //停止selectionKey的监控
                key.cancel();
                //关闭channel
                socketChannel.close();
            }
        } catch (IOException e) {
            log.error("读取服务端响应异常:{}", e.getMessage(), e);
        }
    }
}
