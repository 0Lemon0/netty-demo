package com.hcj.study.nettydemo.codec.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hcj.study.nettydemo.codec.dto.PayCallbackDto;
import com.hcj.study.nettydemo.codec.protobuf.protobuf.PayCallbackReqProtoBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * protobuf编解码测试
 *
 * @author 冰镇柠檬汁
 * @date 2021年06月01日 16:22
 */
@Slf4j
public class TestProtoBufDemo {

    /**
     * 将业务实体转换为protobuf
     * @param dto
     * @return
     */
    private static PayCallbackReqProtoBuf.PayCallbackReq convert(PayCallbackDto dto){
        return PayCallbackReqProtoBuf.PayCallbackReq.newBuilder().setGoodId(dto.getGoodId()).setCount(dto.getCount()).build();
    }

    /**
     * 将protobuf对象编码为byte数组
     * @param payCallbackReq
     * @return
     */
    private static byte[] encode(PayCallbackReqProtoBuf.PayCallbackReq payCallbackReq){
        return payCallbackReq.toByteArray();
    }

    /**
     * 将byte数组解码为protobuf对象
     * @param bytes
     * @return
     * @throws InvalidProtocolBufferException
     */
    private static PayCallbackReqProtoBuf.PayCallbackReq decode(byte[] bytes) throws InvalidProtocolBufferException {
        return PayCallbackReqProtoBuf.PayCallbackReq.parseFrom(bytes);
    }


    public static void main(String[] args) throws InvalidProtocolBufferException {
        PayCallbackDto dto = new PayCallbackDto(100000,2);
        PayCallbackReqProtoBuf.PayCallbackReq payCallbackReq = convert(dto);
        log.info("protobuf:{}",payCallbackReq.toString());
        byte[] data = encode(payCallbackReq);
        log.info("encode:{}",data);
        PayCallbackReqProtoBuf.PayCallbackReq decodePayCallbackReq = decode(data);
        log.info("decode:{}",decodePayCallbackReq);
        log.info("equal:{}", payCallbackReq.equals(decodePayCallbackReq));
    }
}
