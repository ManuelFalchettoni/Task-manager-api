package com.ManuelFalchettoni.task_manager_api.dto.response;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private int status;
    private String message;
    private List<FieldError> errors;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(){}
    public ErrorResponse(int status,String message, List<FieldError> errors){
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public record FieldError(String field, String message) {}

    //Getters and setters
    public int getStatus() {return status;}
    public String getMessage() {return message;}
    public LocalDateTime getTimestamp() {return timestamp;}
}
