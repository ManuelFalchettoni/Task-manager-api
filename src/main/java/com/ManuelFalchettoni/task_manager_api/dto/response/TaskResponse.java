package com.ManuelFalchettoni.task_manager_api.dto.response;

import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus state;
    private LocalDateTime creationDate;

    public TaskResponse(){};
    public TaskResponse(Long id,String title, String description, TaskStatus state,LocalDateTime creationDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.creationDate = creationDate;
    }

    //Getters
    public void setId(Long id){this.id = id;}
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
    public void setState(TaskStatus state){this.state = state;}
    public void setCreationDate(LocalDateTime creationDate){this.creationDate = creationDate;}
}
