package com.redisson.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redisson.enums.RedisDelayQueueEnum;
import com.redisson.utils.RedisDelayQueueUtil;

/**
 * 延迟队列测试
 */
@RestController
public class RedisDelayQueueController {

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Autowired
    private com.redisson.handle.impl.OrderKeyExpire orderKeyExpire;

    @PostMapping("/addQueue1")
    public void addQueue1(Integer count) {
        for (int i = 0; i < count; i++) {
            Integer random = new Random().nextInt(300) + 1;
            Map<String, String> map1 = new HashMap<>();
            map1.put("orderId", String.valueOf(i));
            map1.put("remark", "订单支付超时，自动取消订单");
            map1.put("random", String.valueOf(random));
            map1.put("timestamp", String.valueOf(System.currentTimeMillis()));
            redisDelayQueueUtil.addDelayQueue(map1, random, TimeUnit.SECONDS,
                RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());
        }
    }

    @PostMapping("/addQueue2")
    public void addQueue2(Integer count) {
        for (int i = 0; i < count; i++) {
            Integer random = new Random().nextInt(100) + 1;
            Map<String, String> map1 = new HashMap<>();
            map1.put("orderId", String.valueOf(i));
            map1.put("remark", "订单超时未评价，系统默认好评");
            map1.put("random", String.valueOf(random));
            map1.put("timestamp", String.valueOf(System.currentTimeMillis()));
            redisDelayQueueUtil.addDelayQueue(map1, random, TimeUnit.SECONDS,
                RedisDelayQueueEnum.ORDER_TIMEOUT_NOT_EVALUATED.getCode());
        }
    }

    /**
     * 已经过期的时间节点
     */
    @PostMapping("/addQueue3")
    public void addQueue3(Integer count) {
        for (int i = 0; i < count; i++) {
            // ORDER_PAYMENT_TIMEOUT，队列值：{random=242, orderId=0, remark=订单支付超时，自动取消订单,
            // timestamp=1645270045844}，延迟时间：242秒
            Integer random = new Random().nextInt(300) + 1;
            Map<String, String> map1 = new HashMap<>();
            map1.put("orderId", String.valueOf(i));
            map1.put("remark", "订单超时未评价，系统默认好评");
            map1.put("random", String.valueOf(242));
            map1.put("timestamp", String.valueOf(1645270045844L));
            redisDelayQueueUtil.addDelayQueue(map1, random, TimeUnit.SECONDS,
                RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());
        }
    }

    @PostMapping("/addQueue4")
    public void saveOrder(String code) {
        orderKeyExpire.TestOrderKeyExpire(code);
    }
}
