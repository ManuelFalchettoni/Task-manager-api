package com.ManuelFalchettoni.task_manager_api.dto.request.task;

import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskCreateRequest {
    @NotBlank(message = "The title can't be empty")
    @Size(min = 3, max = 50)
    private String title;
    @Size(min = 3, max = 300)
    private String description;


    public TaskCreateRequest(){}
    public TaskCreateRequest(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String getTitle(){return this.title;}
    public String getDescription(){return this.description;}

}
