package com.esiran.greenpay.message;

import com.esiran.greenpay.message.delayqueue.DelayQueueConsumer;
import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRegister;
import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRunner;
import com.esiran.greenpay.message.delayqueue.entity.DelayMessage;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class DelayQueueRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DelayQueueRunner.class);
    private static final Gson gson = new Gson();
    private final DelayQueueConsumer delayQueueConsumer;
    private final DelayQueueTaskRegister delayQueueTaskRegister;
    public DelayQueueRunner(DelayQueueConsumer delayQueueConsumer,
                            DelayQueueTaskRegister delayQueueTaskRegister) {
        this.delayQueueConsumer = delayQueueConsumer;
        this.delayQueueTaskRegister = delayQueueTaskRegister;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Delay queue running");
        AtomicBoolean exit = new AtomicBoolean(false);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            exit.set(true);
        }));
        List<String> keys = delayQueueTaskRegister.keys();
        for (String key : keys){
            Thread t = new Thread(()->{
                while (!exit.get()){
                    DelayQueueTaskRunner task = delayQueueTaskRegister.get(key);
                    String val = delayQueueConsumer.poll(key,1000);
                    if (val == null) continue;
                    task.exec(val);
                }
            });
            t.start();
        }
    }

    /**
     * 执行通知任务
     * @param message 消息
     */
    private void execNotifyTask(DelayMessage message){
        logger.info("Delay queue exec notify task: {}",gson.toJson(message));

    }
    private static String nextTraceId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
