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
    public Long getId(){return this.id;}
    public void setId(Long id){this.id = id;}

    public String getTitle(){return this.title;}
    public void setTitle(String title){this.title = title;}

    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}

    public TaskStatus getState(){return this.state;}
    public void setState(TaskStatus state){this.state = state;}

    public LocalDateTime getCreationDate(){return this.creationDate;}
    public void setCreationDate(LocalDateTime creationDate){this.creationDate = creationDate;}
}
