package dev.codegood.timesheetmanager.Task;

import dev.codegood.timesheetmanager.Task.exceptions.TaskNotFoundException;
import dev.codegood.timesheetmanager.project.ProjectExceptionHandlers;
import dev.codegood.timesheetmanager.project.exception.ProjectNotFoundException;
import dev.codegood.timesheetmanager.security.SecurityExceptionHandlers;
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
