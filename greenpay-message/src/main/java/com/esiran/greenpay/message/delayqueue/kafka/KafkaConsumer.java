package com.esiran.greenpay.message.delayqueue.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaConsumer {



//    @KafkaListener(topics = "replace",groupId = "group-replace")
//    public void replaceConsumer(String message){
//        log.info(String.format("$$ -> 消费者 replace message --> %s",message));
//
//    }
//    @KafkaListener(topics = "replaceList",groupId = "group-replaces")
//    public void replacesConsumer(String message){
//        log.info(String.format("$$ -> 消费者 replaceList message --> %s",message));
//    }
}
