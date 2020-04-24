package com.esiran.greenpay.message.delayqueue.impl;

import com.esiran.greenpay.common.util.EncryptUtil;
import com.esiran.greenpay.message.delayqueue.DelayQueueSender;
import com.esiran.greenpay.message.delayqueue.entity.DelayMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDelayQueueClient implements DelayQueueSender {
    private final StringRedisTemplate redisTemplate;
    private static final Gson gson = new Gson();
    @Value("${delay_queue.redis.default_key:delay_queue}")
    private String redisKey;
    public RedisDelayQueueClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Override
    public boolean sendDelayMessage(String key, String message, long delayTime) {
        DelayMessage delayMessage = new DelayMessage();
        delayMessage.setId(EncryptUtil.baseTimelineCode());
        delayMessage.setKey(key);
        delayMessage.setContent(message);
        delayMessage.setDelayTime(delayTime);
        String val = gson.toJson(delayMessage);
        long s = System.currentTimeMillis() + delayTime;
        Boolean result = redisTemplate.opsForZSet().add(redisKey,val,s);
        return result==null?false:result;
    }
}
