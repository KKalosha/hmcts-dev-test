package uk.gov.hmcts.reform.dev.models;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

public class Task {

    private Long id;

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String status;

    @NotNull
    @Future
    private LocalDateTime dueDate;

    public Task(Long id, String title, String description, String status, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}