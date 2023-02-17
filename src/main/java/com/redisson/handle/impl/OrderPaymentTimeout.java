package com.redisson.handle.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.redisson.handle.RedisDelayQueueHandle;

import lombok.extern.slf4j.Slf4j;

/**
 * 订单支付超时处理类
 */
@Component
@Slf4j
public class OrderPaymentTimeout implements RedisDelayQueueHandle<Map> {
    @Override
    public void execute(Map map) {
        // TODO 订单支付超时，自动取消订单处理业务...
        Long now = System.currentTimeMillis();
        Long timestamp = Long.valueOf(String.valueOf(map.get("timestamp")));
        Long delayTime = now - timestamp;
        Long random = Long.valueOf(String.valueOf(map.get("random")));
        Long diffTime = delayTime - random * 1000;
        log.info("(OrderPaymentTimeout) orderId：{}, 预计延迟时间：{} 秒，实际延迟时间：{} 毫秒，相差：{} 毫秒", map.get("orderId"), random,
            delayTime, diffTime);
    }
}