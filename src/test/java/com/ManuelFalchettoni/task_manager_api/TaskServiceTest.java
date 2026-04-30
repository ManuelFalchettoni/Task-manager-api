package com.ManuelFalchettoni.task_manager_api;

import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskCreateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import com.ManuelFalchettoni.task_manager_api.repository.task.JpaTaskRepository;
import com.ManuelFalchettoni.task_manager_api.service.task.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class TaskServiceTest {
    @Mock
    JpaTaskRepository jpaTaskRepository;
    @InjectMocks
    TaskService taskService;

    @Test
    void create_ShouldReturnTaskResponse_WhenValidRequest(){
        TaskCreateRequest request = new TaskCreateRequest(
                "Titulo",
                "Description"
        );

        Task task = new Task(
                "Titulo",
                "Description",
                TaskStatus.PENDING
        );

        when(taskService.create(request)).thenReturn(any(TaskResponse.class));
    }
}
