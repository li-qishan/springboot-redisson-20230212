package com.redisson.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.redisson.enums.RedisDelayQueueEnum;
import com.redisson.handle.RedisDelayQueueHandle;
import com.redisson.utils.RedisDelayQueueUtil;
import com.redisson.utils.SpringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动延迟队列
 * 
 */
@Slf4j
@Component
public class RedisDelayQueueRunner implements CommandLineRunner {

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Override
    public void run(String... args) {
        RedisDelayQueueEnum[] queueEnums = RedisDelayQueueEnum.values();
        for (RedisDelayQueueEnum queueEnum : queueEnums) {
            new Thread(() -> {
                try {
                    while (true) {
                        Object value = redisDelayQueueUtil.getDelayQueue(queueEnum.getCode());
                        RedisDelayQueueHandle redisDelayQueueHandle = SpringUtil.getBean(queueEnum.getBeanId());
                        redisDelayQueueHandle.execute(value);
                    }
                } catch (InterruptedException e) {
                    log.error("(Redis延迟队列异常中断) {}", e.getMessage());
                }
            }).start();
        }
        log.info("(Redis延迟队列启动成功)");
    }

}