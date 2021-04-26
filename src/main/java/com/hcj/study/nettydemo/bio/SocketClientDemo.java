package com.hcj.study.nettydemo.bio;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * socket客户端示例
 *
 * @author 冰镇柠檬汁
 * @date 2021年04月22日 16:29
 */
@Slf4j
public class SocketClientDemo{
    private static String ORDER_CLOSE = "CLOSE";
    private static Socket socket;
    private static int localPort = NetUtil.getUsableLocalPort();

    public static void main(String[] args) {
        while(true){
            socketInit();
            try (//读取服务器端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                //向服务器端发送数据
                DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
                log.info("请输入指令:\t");
                //String order = new BufferedReader(new InputStreamReader(System.in)).readLine();
                String order = "CURRENT_TIME";
                if(ORDER_CLOSE.equalsIgnoreCase(order)){
                    break;
                }
                out.writeUTF(order);
                log.info("服务端响应:{}",input.readUTF());
            } catch(IOException e){
                log.error("ERROR:{}",e.getMessage(),e);
                break;
            }
        }
    }

    private static void socketInit(){
        try {
            socket = new Socket();
            //固定socket本地端口
            socket.bind(new InetSocketAddress("127.0.0.1",localPort));
            socket.connect(new InetSocketAddress("127.0.0.1",8010));
        } catch (BindException be){
            localPort = NetUtil.getUsableLocalPort();
            socketInit();
        } catch (IOException e) {
            log.info("socket创建失败:{}",e.getMessage(),e);
        }
    }
}
