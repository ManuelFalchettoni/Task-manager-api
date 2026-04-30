package com.ManuelFalchettoni.task_manager_api;

import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskCreateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import com.ManuelFalchettoni.task_manager_api.mapper.TaskMapper;
import com.ManuelFalchettoni.task_manager_api.repository.task.JpaTaskRepository;
import com.ManuelFalchettoni.task_manager_api.service.task.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    JpaTaskRepository jpaTaskRepository;
    @InjectMocks
    TaskService taskService;

    @Test
    void create_ShouldReturnTaskResponse_WhenValidRequest(){
        // GIVEN (Preparación)
        TaskCreateRequest request = new TaskCreateRequest(
                "Titulo",
                "Description"
        );
        // Configuramos el comportamiento: when -> thenReturn
        Task task = TaskMapper.requestToTask(request);
        ReflectionTestUtils.setField(task, "id", 1L);
        when(jpaTaskRepository.save(any(Task.class))).thenReturn(task);

        // WHEN (Acción)
        TaskResponse response = taskService.create(request);

        // THEN (Verificación)
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Titulo", response.getTitle());
        assertEquals("Description", response.getDescription());
        assertEquals(TaskStatus.PENDING, response.getState());

    }
}
