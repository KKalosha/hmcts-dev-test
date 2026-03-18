package uk.gov.hmcts.reform.dev.models;

import java.time.LocalDateTime;

public class Task {

    private Long id;
    private String title;
    private String description;
    private String status;
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