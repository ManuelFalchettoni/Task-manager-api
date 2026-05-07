package com.ManuelFalchettoni.task_manager_api;

import com.ManuelFalchettoni.task_manager_api.controller.task.TaskController;
import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskCreateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskUpdateRequest;
import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import com.ManuelFalchettoni.task_manager_api.exception.task.TaskNotFoundException;
import com.ManuelFalchettoni.task_manager_api.mapper.TaskMapper;
import com.ManuelFalchettoni.task_manager_api.service.task.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    //GET
    @Test
    void get_ShouldReturnTaskResponse_WhenValidId() throws Exception {
        Task task = new Task(
                "Titulo",
                "Description"
        );
        ReflectionTestUtils.setField(task, "id", 1L);
        Mockito.when(taskService.findTask(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //Http 200
                .andExpect(jsonPath("$.title").value("Titulo"))
                .andExpect(jsonPath("$.description").value("Description"));

    }

    @Test
    void get_ShouldThrowException_WhenInvalidId() throws Exception {
        Mockito.when(taskService.findTask(1L)).thenThrow(new TaskNotFoundException(1L));

        mockMvc.perform(get("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: " + 1L));
    }

    //POST
    @Test
    void post_ShouldReturnTaskResponse_WhenValidRequest() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest(
                "Title",
                "Description"
        );
        Task task = TaskMapper.requestToTask(request);
        ReflectionTestUtils.setField(task, "id", 1L);

        Mockito.when(taskService.create(any(TaskCreateRequest.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    void post_ShouldThrowException_WhenEmptyTitle() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest(null, "Description");
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    //PUT
    @Test
    void put_ShouldReturnTaskResponse_WhenValidRequest() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest("Title", "Description", TaskStatus.IN_PROGRESS);
        Task task = new Task("Title", "Description");
        task.setState(TaskStatus.IN_PROGRESS);
        ReflectionTestUtils.setField(task, "id", 1L);

        Mockito.when(taskService.update(eq(1L), any(TaskUpdateRequest.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.state").value("IN_PROGRESS"));
    }

    @Test
    void put_ShouldThrowException_WhenInvalidId() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest("Title", "Description", TaskStatus.IN_PROGRESS);
        Mockito.when(taskService.update(eq(1L), any(TaskUpdateRequest.class))).thenThrow(new TaskNotFoundException(1L));

        mockMvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: " + 1L));
    }

    @Test
    void put_ShouldThrowException_WhenInvalidRequest() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest("Title", "De", TaskStatus.IN_PROGRESS);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    //Delete
    @Test
    void delete_ShouldDeleteTask_WhenValidId() throws Exception {
        mockMvc.perform(delete("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_ShouldThrowException_WhenInvalidId() throws Exception {
        doThrow(new TaskNotFoundException(1L)).when(taskService).delete(1L); //doThrow for methods void
        mockMvc.perform(delete("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: " + 1L));
    }

    //Get all
    @Test
    void getAll_ShouldReturnPage() throws Exception {
        List<Task> tasks = List.of(
                new Task("Titulo", "Descripcion"),
                new Task("Title", "Description")
        );

        Page<Task> page = new PageImpl<>(tasks, PageRequest.of(0, 10), tasks.size());

        Mockito.when(taskService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/tasks")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Titulo"))
                .andExpect(jsonPath("$.content[0].description").value("Descripcion"))
                .andExpect(jsonPath("$.content[1].title").value("Title"))
                .andExpect(jsonPath("$.content[1].description").value("Description"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0));

    }
}
