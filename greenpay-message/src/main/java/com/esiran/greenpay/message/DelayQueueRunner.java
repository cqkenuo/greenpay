package com.esiran.greenpay.message;

import com.esiran.greenpay.message.delayqueue.DelayQueueConsumer;
import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRegister;
import com.esiran.greenpay.message.delayqueue.DelayQueueTaskRunner;
import com.esiran.greenpay.message.delayqueue.entity.DelayMessage;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class DelayQueueRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(DelayQueueRunner.class);
    private final DelayQueueConsumer delayQueueConsumer;
    private final DelayQueueTaskRegister delayQueueTaskRegister;
    private ExecutorService es;
    private static final float THREAD_CHOKE_TIME_SECOND = 2.0f;
    private static final int POOL_SIZE;
    @Value("${delay_queue.consumer.enabled:false}")
    private Boolean enabled;
    static {
        long poolSize = Runtime.getRuntime().availableProcessors();
        poolSize =  ((Math.round((THREAD_CHOKE_TIME_SECOND / 0.15))+1) * poolSize);
        POOL_SIZE = (int) poolSize;
    }
    public DelayQueueRunner(DelayQueueConsumer delayQueueConsumer,
                            DelayQueueTaskRegister delayQueueTaskRegister) {
        this.delayQueueConsumer = delayQueueConsumer;
        this.delayQueueTaskRegister = delayQueueTaskRegister;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!enabled) return;
        logger.info("Delay queue running, pool_size: {}", POOL_SIZE);
        es = Executors.newFixedThreadPool(POOL_SIZE);
        AtomicBoolean exit = new AtomicBoolean(false);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            exit.set(true);
            delayQueueConsumer.close();
        }));
        Thread t = new Thread(()->{
            while (!exit.get()){
                DelayMessage message = delayQueueConsumer.poll(500);
                if (message == null) continue;
                DelayQueueTaskRunner task = delayQueueTaskRegister.get(message.getKey());
                if (task == null) continue;
                es.execute(()->task.exec(message.getContent()));
            }
        });
        t.start();
    }
}
