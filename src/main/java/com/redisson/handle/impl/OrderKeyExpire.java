package com.redisson.handle.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liu
 */
@Service
@Slf4j
public class OrderKeyExpire {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void TestOrderKeyExpire(String code) {
        Integer random = new Random().nextInt(100) + 1;
        // 保存到Redis中，并设置过期时间 100s 内随机时间
        this.redisTemplate.opsForValue().set("delay-queue-key-expire-" + code + "", code, random, TimeUnit.SECONDS);
        log.info("向队列中推送 上级视图库key： delay-queue-key-expire-{} 过期时间:{} s", code, random);
        // 处理保存的逻辑
    }

}