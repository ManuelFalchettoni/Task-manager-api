package com.ManuelFalchettoni.task_manager_api.repository.task;

import com.ManuelFalchettoni.task_manager_api.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface JpaTaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
}
