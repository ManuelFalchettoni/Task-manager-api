package com.ManuelFalchettoni.task_manager_api.dto.request;

import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequest {
    @NotBlank
    @Size(max = 50)
    private String title;
    private String description;
    private TaskStatus state;

    public TaskRequest(){};
    public TaskRequest(String title, String description, TaskStatus state){
        this.title = title;
        this.description = description;
        this.state = state;
    }

    //Getters

    public String getTitle(){return this.title;}
    public String getDescription(){return this.description;}
    public TaskStatus getState(){return this.state;}

}
