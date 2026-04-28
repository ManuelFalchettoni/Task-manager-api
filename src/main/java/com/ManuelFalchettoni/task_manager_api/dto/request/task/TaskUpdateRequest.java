package com.ManuelFalchettoni.task_manager_api.dto.request.task;

import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import jakarta.validation.constraints.Size;

public class TaskUpdateRequest {
    @Size(min = 3, max = 50)
    private String title;
    @Size(min = 3, max = 300)
    private String description;
    private TaskStatus state;

    public TaskUpdateRequest(){}

    public TaskUpdateRequest(String title, String description, TaskStatus state){
        this.title = title;
        this.description = description;
        this.state = state;
    }

    //Getters
    public String getTitle(){return this.title;}
    public String getDescription(){return this.description;}
    public TaskStatus getState(){return this.state;}
}
