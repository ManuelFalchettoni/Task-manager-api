package com.ManuelFalchettoni.task_manager_api.mapper;

import com.ManuelFalchettoni.task_manager_api.dto.request.TaskRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;

public class TaskMapper {

    public static TaskResponse taskToResponse(Task task){
        return new TaskResponse (task.getId(), task.getTitle(), task.getDescription(), task.getState(), task.getCreationDate());
    }

    public static Task requestToTask(TaskRequest taskRequest){
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setState(taskRequest.getState());
        return task;
    }
}
