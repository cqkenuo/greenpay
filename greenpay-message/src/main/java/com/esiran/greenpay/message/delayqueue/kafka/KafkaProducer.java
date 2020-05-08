package com.esiran.greenpay.message.delayqueue.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class KafkaProducer {


    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void replaceProducer(String message){
        log.info(String.format("$$ -> 生产者 replace message --> %s",message));
        ListenableFuture<SendResult<String, String>> future  = this.kafkaTemplate.send("replace", message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("消息：{} 发送失败，原因：{}", message, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("成功发送消息：{}，offset=[{}]", message, result.getRecordMetadata().offset());
            }
        });
    }

//    public void replaceListProducer(String message){
//        log.info(String.format("$$ -> 生产者 replace message --> %s",message));
//        ListenableFuture<SendResult<String, String>> future  = this.kafkaTemplate.send("replaceList", message);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                log.info("消息：{} 发送失败，原因：{}", message, throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                log.info("成功发送消息：{}，offset=[{}]", message, result.getRecordMetadata().offset());
//            }
//        });
//    }
}
