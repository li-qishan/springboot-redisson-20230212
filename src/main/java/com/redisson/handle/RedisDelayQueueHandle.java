package com.redisson.handle;

/**
 * 延迟队列执行器
 */
public interface RedisDelayQueueHandle<T> {

    void execute(T t);

}