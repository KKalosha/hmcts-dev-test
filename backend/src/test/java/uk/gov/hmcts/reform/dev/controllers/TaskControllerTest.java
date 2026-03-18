package uk.gov.hmcts.reform.dev.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import uk.gov.hmcts.reform.dev.dto.StatusUpdateRequest;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        objectMapper.findAndRegisterModules();

        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void shouldGetAllTasks() throws Exception {

        Task task = new Task(
                1L,
                "Test task",
                "Test description",
                "TODO",
                LocalDateTime.now().plusDays(1)
        );

        when(taskRepository.findAll()).thenReturn(List.of(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test task"));
    }

    @Test
    void shouldCreateTask() throws Exception {

        Task requestTask = new Task(
                null,
                "New task",
                "New description",
                "TODO",
                LocalDateTime.now().plusDays(1)
        );

        Task savedTask = new Task(
                1L,
                "New task",
                "New description",
                "TODO",
                requestTask.getDueDate()
        );

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldGetTaskById() throws Exception {

        Task task = new Task(
                1L,
                "Test task",
                "Test description",
                "TODO",
                LocalDateTime.now().plusDays(1)
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test task"));
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTask() throws Exception {

        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk());

        verify(taskRepository).deleteById(1L);
    }

    @Test
    void shouldReturn404WhenDeletingMissingTask() throws Exception {

        when(taskRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateTaskStatus() throws Exception {

        Task existingTask = new Task(
                1L,
                "Test task",
                "Test description",
                "TODO",
                LocalDateTime.now().plusDays(1)
        );

        StatusUpdateRequest request = new StatusUpdateRequest();
        request.setStatus("DONE");

        Task updatedTask = new Task(
                1L,
                "Test task",
                "Test description",
                "DONE",
                existingTask.getDueDate()
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(patch("/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void shouldReturn404WhenUpdatingMissingTask() throws Exception {

        StatusUpdateRequest request = new StatusUpdateRequest();
        request.setStatus("DONE");

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}