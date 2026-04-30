package com.ManuelFalchettoni.task_manager_api.entity.task;

import com.ManuelFalchettoni.task_manager_api.enums.TaskStatus;
import com.ManuelFalchettoni.task_manager_api.repository.task.JpaTaskRepository;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "task") //Reference to DB
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;


    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus state = TaskStatus.PENDING;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    public Task(){}//Empty for the ORM

    public Task(String title, String description){
        this.title = title;
        this.description= description;
    }

    @PrePersist
    protected void onCreate() {
        if (this.state == null) {
            this.state = TaskStatus.PENDING;
        }
    }

    //Getters and setters
    public Long getId(){return this.id;}

    public String getTitle(){return this.title;}
    public void setTitle(String title){this.title = title;}

    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}

    public TaskStatus getState(){return this.state;}
    public void setState(TaskStatus state){this.state = state;}

    public LocalDateTime getCreationDate(){return this.creationDate;}
}
