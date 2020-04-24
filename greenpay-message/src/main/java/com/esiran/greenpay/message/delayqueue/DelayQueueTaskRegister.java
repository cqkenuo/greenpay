package com.esiran.greenpay.message.delayqueue;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DelayQueueTaskRegister {
    private Map<String,DelayQueueTaskRunner> taskRunnerMap;
    public DelayQueueTaskRegister() {
        this.taskRunnerMap  = new HashMap<>();
    }
    public void register(String key, DelayQueueTaskRunner runner){
        taskRunnerMap.put(key,runner);
    }
    public List<String> keys(){
        return new ArrayList<>(taskRunnerMap.keySet());
    }
    public DelayQueueTaskRunner get(String key){
        return taskRunnerMap.get(key);
    }
}
