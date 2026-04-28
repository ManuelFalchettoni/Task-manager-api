package com.ManuelFalchettoni.task_manager_api.controller.task;

import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskCreateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.request.task.TaskUpdateRequest;
import com.ManuelFalchettoni.task_manager_api.dto.response.TaskResponse;
import com.ManuelFalchettoni.task_manager_api.service.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskCreateRequest taskRequest) {
        TaskResponse response = taskService.create(taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> find(@PathVariable Long id){
        TaskResponse response = taskService.find(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest request){
        TaskResponse response = taskService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> findAll(Pageable pageable){
        Page<TaskResponse>  responses= taskService.findAll(pageable);
        return ResponseEntity.ok(responses);
    }
}
