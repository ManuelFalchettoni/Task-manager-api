package com.ManuelFalchettoni.task_manager_api.exception.task;


public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task not found with id: "+ id);
    }

}
