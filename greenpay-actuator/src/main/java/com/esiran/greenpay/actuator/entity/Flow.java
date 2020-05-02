package com.esiran.greenpay.actuator.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Flow<T> {
    private List<Task<T>> tasks;
    private final T data;
    private Map<String,Object> results;
    public Flow(T data) {
        this.data = data;
        this.tasks = new ArrayList<>();
    }
    public void execDependent(String taskName) throws Exception {
        for (Task<T> task : tasks){
            if (task.dependent().equals(taskName)){
                task.action(this);
                execDependent(task.taskName());
            }
        }
    }

    public List<Task<T>> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task<T>> tasks) {
        this.tasks = tasks;
    }

    public void add(Task<T> task){
        tasks.add(task);
    }
    public T getData(){
        return data;
    }
    public void returns(Map<String,Object> results){
        this.results = results;
    }
    public Map<String,Object> getResults(){
        return results;
    }
}
