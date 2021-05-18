package com.hcj.study.nettydemo.bio;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.hcj.study.nettydemo.constants.Constants.ORDER_CLOSE;

/**
 * socket客户端示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年04月22日 16:29
 */
@Slf4j
public class SocketClientDemo {
    private static Socket socket;
    private static int localPort = NetUtil.getUsableLocalPort();

    static {
        try {
            socket = new Socket();
            //固定socket本地端口
            socket.bind(new InetSocketAddress("127.0.0.1", localPort));
            socket.connect(new InetSocketAddress("127.0.0.1", 8010));
        } catch (IOException e) {
            log.info("socket创建失败:{}", e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        try (//读取服务器端数据
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //向服务器端发送数据
            DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                log.info("请输入指令:\t");
                String order = new BufferedReader(new InputStreamReader(System.in)).readLine();
                //String order = "CURRENT_TIME";
                //阻塞至所有要发送的字节写入完毕
                out.writeUTF(order);
                if (ORDER_CLOSE.equalsIgnoreCase(order)) {
                    break;
                }
                //input.readUTF()阻塞至服务端响应
                log.info("服务端响应:{}", input.readUTF());
            }
        } catch (IOException e) {
            log.error("ERROR:{}", e.getMessage(), e);
        }
    }
}
