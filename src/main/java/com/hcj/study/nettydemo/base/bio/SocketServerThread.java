package com.hcj.study.nettydemo.base.bio;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static com.hcj.study.nettydemo.base.constants.Constants.*;

/**
 * socket服务端处理线程
 *
 * @author 冰镇柠檬汁
 * @date 2021年05月13日 14:20
 */
@Slf4j
public class SocketServerThread implements Runnable{
    private Socket socket;

    public SocketServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (//读取服务器端数据
             //向服务器端发送数据
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())){
            while(true){
                //读取客户端输入的指令,input.readUTF()阻塞至客户端请求
                String order = input.readUTF();
                String response;
                if(ORDER_CLOSE.equalsIgnoreCase(order)){
                    break;
                }
                log.info("input order is {}",order);
                response = VALID_ORDER.equalsIgnoreCase(order)? DateUtil.now():INVALID_ORDER_TIPS;
                //阻塞至所有要发送的字节写入完毕
                out.writeUTF(response);
            }
        } catch (Exception e) {
            log.error("socket[{}] io error:{}",socket.getRemoteSocketAddress(),e.getMessage(),e);
        }
        log.info("connection close:{}",socket.getRemoteSocketAddress());
    }
}
