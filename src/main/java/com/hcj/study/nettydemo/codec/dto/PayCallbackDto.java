package com.hcj.study.nettydemo.codec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付回调实体
 *
 * @author 冰镇柠檬汁
 * @date 2021年06月01日 16:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayCallbackDto {
    private long goodId;
    private int count;
}
