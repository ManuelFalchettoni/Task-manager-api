package com.ManuelFalchettoni.task_manager_api.service.task;

import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskCreateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskUpdateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import com.ManuelFalchettoni.task_manager_api.exception.task.TaskNotFoundException;
import com.ManuelFalchettoni.task_manager_api.mapper.TaskMapper;
import com.ManuelFalchettoni.task_manager_api.repository.task.JpaTaskRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final JpaTaskRepository jpaTaskRepository;

    public TaskService(JpaTaskRepository jpaTaskRepository){
        this.jpaTaskRepository = jpaTaskRepository;
    }

    public TaskResponse create(TaskCreateRequest taskRequest){
        Task task = TaskMapper.requestToTask(taskRequest);
        Task savedTask = jpaTaskRepository.save(task);
        return TaskMapper.taskToResponse(savedTask);
    }

    private Task findTask(Long id){
        return jpaTaskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }
    public TaskResponse find(Long id){
        return TaskMapper.taskToResponse(findTask(id));
    }

    public Page<TaskResponse> findAll(Pageable pageable){
        Page<Task> taskPage = jpaTaskRepository.findAll(pageable);
        return taskPage.map(TaskMapper::taskToResponse);
    }

    public TaskResponse update(Long id, TaskUpdateRequest request){
        Task task = TaskMapper.updateTaskFromRequest(findTask(id), request);
        Task save = jpaTaskRepository.save(task);
        return TaskMapper.taskToResponse(save);
    }

    public void delete(Long id){
        findTask(id);
        jpaTaskRepository.deleteById(id);
    }
}
