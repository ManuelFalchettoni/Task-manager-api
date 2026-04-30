package com.ManuelFalchettoni.task_manager_api;

import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskCreateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskUpdateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import com.ManuelFalchettoni.task_manager_api.exception.task.TaskNotFoundException;
import com.ManuelFalchettoni.task_manager_api.mapper.TaskMapper;
import com.ManuelFalchettoni.task_manager_api.repository.task.JpaTaskRepository;
import com.ManuelFalchettoni.task_manager_api.service.task.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Locale;
import java.util.Optional;

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
        // when -> thenReturn
        Task task = TaskMapper.requestToTask(request);
        ReflectionTestUtils.setField(task, "id", 1L);
        when(jpaTaskRepository.save(any(Task.class))).thenReturn(task);

        // WHEN (Acción)
        TaskResponse response = taskService.create(request);

        // THEN
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Titulo", response.getTitle());
        assertEquals("Description", response.getDescription());
        assertEquals(TaskStatus.PENDING, response.getState());
    }

    @Test
    void find_ShouldReturnTask_WhenValidID() {
        Task task = new Task(
                "Titulo",
                "Description"
        );
        ReflectionTestUtils.setField(task, "id", 1L);
        when(jpaTaskRepository.findById(1L)).thenReturn(Optional.of(task));

        //When
        TaskResponse response = taskService.find(1L);

        //Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Titulo", response.getTitle());
        assertEquals("Description", response.getDescription());
        assertEquals(TaskStatus.PENDING, response.getState());
    }

    @Test
    void find_ShouldThrowException_WhenInvalidID(){
        // GIVEN: Set up the mock to return an empty box (Optional.empty)
        when(jpaTaskRepository.findById(1L)).thenReturn(Optional.empty()); //

        // WHEN & THEN: Assert that the exception is thrown
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () ->{
            taskService.find(1L);
        });

        assertEquals("Task not found with id: " + 1L, exception.getMessage());
    }

    @Test
    void update_ShouldReturnTask_WhenValidRequest(){
        // GIVEN
        TaskUpdateRequest request = new TaskUpdateRequest("Title", "Description 2", TaskStatus.IN_PROGRESS);

        Task task = new Task("Titulo", "Description");
        ReflectionTestUtils.setField(task, "id", 1L);


        when(jpaTaskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(jpaTaskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.update(1L, request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Title", response.getTitle());
        assertEquals("Description 2", response.getDescription());
    }

    @Test
    void update_ShouldThrowException_WhenTaskNotFound(){
        // GIVEN
        TaskUpdateRequest request = new TaskUpdateRequest("Title", "Description 2", TaskStatus.IN_PROGRESS);
        when(jpaTaskRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN & THEN: Assert that the exception is thrown
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () ->{
            taskService.update(1L,request);
        });

        assertEquals("Task not found with id: " + 1L, exception.getMessage());

    }
}
