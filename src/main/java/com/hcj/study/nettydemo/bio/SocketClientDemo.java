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
    private static DataInputStream input;
    private static DataOutputStream out;

    static {
        try {
            socket = new Socket();
            //固定socket本地端口
            socket.bind(new InetSocketAddress("127.0.0.1",localPort));
            socket.connect(new InetSocketAddress("127.0.0.1",8010));
        } catch (IOException e) {
            log.info("socket创建失败:{}",e.getMessage(),e);
        }
    }

    public static void main(String[] args) {
        while(true){
            try {
                //读取服务器端数据
                input = new DataInputStream(socket.getInputStream());
                //向服务器端发送数据
                out = new DataOutputStream(socket.getOutputStream());
                log.info("请输入指令:\t");
                String order = new BufferedReader(new InputStreamReader(System.in)).readLine();
                //String order = "CURRENT_TIME";
                out.writeUTF(order);
                if(ORDER_CLOSE.equalsIgnoreCase(order)){
                    close();
                    break;
                }
                //input.readUTF()阻塞至服务端响应
                log.info("服务端响应:{}",input.readUTF());
            } catch(IOException e){
                log.error("ERROR:{}",e.getMessage(),e);
                close();
                break;
            }
        }
    }

    /**
     * 关闭资源
     */
    private static void close() {
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                log.error("socket close error:{}",e.getMessage(),e);
            }
        }
        if(input!=null){
            try {
                input.close();
            } catch (IOException e) {
                log.error("inputSteam close error:{}",e.getMessage(),e);
            }
        }
        if(out!=null){
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                log.error("outputSteam close error:{}",e.getMessage(),e);
            }
        }
    }
}
