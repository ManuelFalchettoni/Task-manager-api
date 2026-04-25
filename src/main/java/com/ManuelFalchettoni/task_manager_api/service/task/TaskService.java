package com.ManuelFalchettoni.task_manager_api.service.task;

import com.ManuelFalchettoni.task_manager_api.dto.request.TaskRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import com.ManuelFalchettoni.task_manager_api.mapper.TaskMapper;
import com.ManuelFalchettoni.task_manager_api.repository.task.JpaTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final JpaTaskRepository jpaTaskRepository;

    public TaskService(JpaTaskRepository jpaTaskRepository){
        this.jpaTaskRepository = jpaTaskRepository;
    }

    public TaskResponse create(TaskRequest taskRequest){
        Task task = TaskMapper.requestToTask(taskRequest);
        Task savedTask = jpaTaskRepository.save(task);
        return TaskMapper.taskToResponse(savedTask);
    }
}
