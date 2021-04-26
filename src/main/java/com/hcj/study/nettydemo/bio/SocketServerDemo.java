package com.hcj.study.nettydemo.bio;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket服务端示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年04月22日 15:46
 */
@Slf4j
public class SocketServerDemo {
    private static ServerSocket serverSocket;
    private static String VALID_ORDER = "CURRENT_TIME";
    private static String INVALID_ORDER_TIPS = "invalid order";
    static{
        try {
            int port = 8010;
            serverSocket = new ServerSocket(port);
            log.info("socket server启动成功,端口:{}",port);
        } catch (IOException e) {
            log.error("socket server启动异常:{}",e.getMessage(),e);
        }
    }

    public static void main(String[] args) {
        while(true){
            //读取服务器端数据
            //向服务器端发送数据
            try (Socket socket = serverSocket.accept();
                 DataInputStream in =new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
                log.info("收到来自客户端[{}]的连接",socket.getRemoteSocketAddress());
                //读取客户端输入的指令
                String order = in.readUTF();
                String response;
                if(StrUtil.isBlank(order)){
                    continue;
                }
                log.info("input order is {}",order);
                response = VALID_ORDER.equalsIgnoreCase(order)? DateUtil.now():INVALID_ORDER_TIPS;
                out.writeUTF(response);
            } catch (IOException e) {
                log.error("socket io error:{}",e.getMessage(),e);
            }
        }
    }
}
