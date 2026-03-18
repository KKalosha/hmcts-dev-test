package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.dto.StatusUpdateRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        tasks.add(task);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        tasks.removeIf(t -> t.getId().equals(id));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
     public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        return tasks.stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .map(task -> {
                task.setStatus(request.getStatus());
                return ResponseEntity.ok(task);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}