package com.ManuelFalchettoni.task_manager_api.mapper;

import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskCreateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskUpdateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;

public class TaskMapper {

    public static TaskResponse taskToResponse(Task task){
        return new TaskResponse (task.getId(), task.getTitle(), task.getDescription(), task.getState(), task.getCreationDate());
    }

    public static Task requestToTask(TaskCreateRequest taskRequest){
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        return task;
    }

    public static Task updateTaskFromRequest(Task task, TaskUpdateRequest request){
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getState() != null) {
            task.setState(request.getState());
        }
        return task;
    }
}
