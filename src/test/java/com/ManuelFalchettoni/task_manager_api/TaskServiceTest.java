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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        assertEquals(TaskStatus.IN_PROGRESS, response.getState());
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

    @Test
    void delete_ShouldDeleteTask_WhenIdExists(){
        Long taskId = 1L;
        Task task = new Task();
        ReflectionTestUtils.setField(task, "id", taskId);

        when(jpaTaskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.delete(taskId);

        verify(jpaTaskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void delete_ShouldThrowException_WhenTaskNotFound(){
        Long taskId = 1L;

        when(jpaTaskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () ->{
            taskService.delete(taskId);
        });
        assertEquals("Task not found with id: " + taskId, exception.getMessage());
    }

    @Test
    void findAll_ShouldReturnPage(){

        Task task1 = new Task("First Task", "First Description");
        Task task2 = new Task("Second Task", "Second Description");
        ReflectionTestUtils.setField(task1,"id", 1L);
        ReflectionTestUtils.setField(task2,"id", 2L);
        List<Task> taskList = List.of(task1,task2);

        Pageable pageable = PageRequest.of(0,10);
        Page<Task> taskPage = new PageImpl<>(taskList,pageable,taskList.size());

        when(jpaTaskRepository.findAll(pageable)).thenReturn(taskPage);

        Page<TaskResponse> response = taskService.findAll(pageable);

        assertNotNull(response);
        assertEquals(2, response.getContent().size());//Extract and count the list
        assertEquals("First Task",response.getContent().get(0).getTitle()); //extract content of the first element and get the title

        verify(jpaTaskRepository).findAll(pageable);

    }

}
