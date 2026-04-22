package com.ManuelFalchettoni.task_manager_api.dto.response.task;

import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus state;
    private LocalDateTime creationDate;

    public TaskResponse(Long id, String title, String description, TaskStatus state, LocalDateTime creationDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.creationDate = creationDate;
    }

    public Long getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getDescription(){return this.description;}
    public TaskStatus getState(){return this.state;}
    public LocalDateTime getCreationDate(){return this.creationDate;}

}
