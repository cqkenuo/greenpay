package com.esiran.greenpay.message.delayqueue.impl;

import com.esiran.greenpay.message.delayqueue.DelayQueueConsumer;
import com.esiran.greenpay.message.delayqueue.entity.DelayMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisDelayQueueConsumer implements DelayQueueConsumer {
    private final StringRedisTemplate redisTemplate;
    private static final Gson gson = new Gson();
    @Value("${delay_queue.redis.default_key:delay_queue}")
    private String redisKey;
    private boolean close = false;
    public RedisDelayQueueConsumer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String poll(String key, long timeout) {
        synchronized (Object.class){
            if (timeout < 0)
                throw new IllegalArgumentException("Timeout must not be negative");
            long remaining = 0;
            do {
                long start = System.currentTimeMillis();
                if (close) continue;
                Set<String> set = redisTemplate.opsForZSet().rangeByScore(key,0,System.currentTimeMillis());
                if (set != null && set.size() > 1){
                    String itemString = set.stream().findFirst().get();
                    DelayMessage message = gson.fromJson(itemString, DelayMessage.class);
                    if (message!=null){
                        String content = message.getContent();
                        redisTemplate.opsForZSet().remove(key,itemString);
                        return content;
                    }
                }
                long elapsed = System.currentTimeMillis() - start;
                remaining = timeout - elapsed;
            }while (remaining > 0);
        }
        return null;
    }

    @Override
    public DelayMessage poll(long timeout) {
        synchronized (Object.class){
            if (timeout < 0)
                throw new IllegalArgumentException("Timeout must not be negative");
            long remaining = 0;
            do {
                long start = System.currentTimeMillis();
                if (close) continue;
                Set<String> set = redisTemplate.opsForZSet().rangeByScore(redisKey,0,System.currentTimeMillis());
                if (set != null && set.size() > 1){
                    String itemString = set.stream().findFirst().get();
                    DelayMessage message = gson.fromJson(itemString, DelayMessage.class);
                    if (message!=null){
                        redisTemplate.opsForZSet().remove(redisKey,itemString);
                        return message;
                    }
                }
                long elapsed = System.currentTimeMillis() - start;
                remaining = timeout - elapsed;
            }while (remaining > 0);
        }
        return null;
    }

    @Override
    public void close() {
        close = true;
    }

}
