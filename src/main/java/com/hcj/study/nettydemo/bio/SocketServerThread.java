package com.hcj.study.nettydemo.bio;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * socket服务端处理线程
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月13日 14:20
 */
@Slf4j
public class SocketServerThread implements Runnable{
    private static String ORDER_CLOSE = "CLOSE";
    private static String VALID_ORDER = "CURRENT_TIME";
    private static String INVALID_ORDER_TIPS = "invalid order";

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream out;


    public SocketServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            if(socket.isClosed()){
                break;
            }
            //读取服务器端数据
            //向服务器端发送数据
            try {
                input = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                //读取客户端输入的指令,input.readUTF()阻塞至客户端请求
                String order = input.readUTF();
                String response;
                if(ORDER_CLOSE.equalsIgnoreCase(order)){
                    close();
                    break;
                }
                log.info("input order is {}",order);
                response = VALID_ORDER.equalsIgnoreCase(order)? DateUtil.now():INVALID_ORDER_TIPS;
                out.writeUTF(response);
            } catch (IOException e) {
                log.error("socket[{}] io error:{}",socket.getRemoteSocketAddress(),e.getMessage(),e);
                close();
                break;
            }
        }
    }

    /**
     * 关闭资源
     */
    private void close() {
        SocketAddress remoteAddress = socket.getRemoteSocketAddress();
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
        log.info("connection closed:[{}]",remoteAddress);
    }
}
