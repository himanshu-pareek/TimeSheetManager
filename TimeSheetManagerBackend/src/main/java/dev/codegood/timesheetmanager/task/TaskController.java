package dev.codegood.timesheetmanager.task;

import dev.codegood.timesheetmanager.task.exceptions.TaskInvalidException;
import dev.codegood.timesheetmanager.task.exceptions.TaskNotFoundException;
import dev.codegood.timesheetmanager.project.ProjectExceptionHandlers;
import dev.codegood.timesheetmanager.project.exception.ProjectNotFoundException;
import dev.codegood.timesheetmanager.security.SecurityExceptionHandlers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskModelAssembler taskModelAssembler;

    public TaskController(TaskService taskService, TaskModelAssembler taskModelAssembler) {
        this.taskService = taskService;
        this.taskModelAssembler = taskModelAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneById(
            @PathVariable("id") String id
    ) {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<?> createOne(
            @RequestBody Task task
    ) {
        try {
            task = taskService.createTask(task);
        } catch (TaskInvalidException e) {
            return TaskExceptionHandlers.handleTaskInvalidException(e);
        } catch (ProjectNotFoundException e) {
            return ProjectExceptionHandlers.handleProjectNotFoundException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                taskModelAssembler.toModel(task)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(
            @PathVariable("id") String id
    ) {
        Task task = null;
        try {
            task = taskService.deleteTask(id);
        } catch (TaskNotFoundException e) {
            return TaskExceptionHandlers.handleTaskNotFoundException(e);
        } catch (ProjectNotFoundException e) {
            return ProjectExceptionHandlers.handleProjectNotFoundException(e);
        } catch (AccessDeniedException e) {
            return SecurityExceptionHandlers.handleAccessDeniedException(e);
        }
        return ResponseEntity.ok(
                taskModelAssembler.toModel(task)
        );
    }
}
