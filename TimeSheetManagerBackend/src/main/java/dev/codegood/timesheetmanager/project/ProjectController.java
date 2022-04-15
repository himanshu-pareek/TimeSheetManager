package dev.codegood.timesheetmanager.project;

import dev.codegood.timesheetmanager.Task.Task;
import dev.codegood.timesheetmanager.Task.TaskModelAssembler;
import dev.codegood.timesheetmanager.Task.TaskService;
import dev.codegood.timesheetmanager.project.exception.ProjectMissingAttributeException;
import dev.codegood.timesheetmanager.project.exception.ProjectNotFoundException;
import dev.codegood.timesheetmanager.project.exception.ProjectStateException;
import dev.codegood.timesheetmanager.security.SecurityExceptionHandlers;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final ProjectModelAssembler projectModelAssembler;
    private final TaskModelAssembler taskModelAssembler;

    public ProjectController(
            ProjectService projectService,
            TaskService taskService,
            ProjectModelAssembler projectModelAssembler,
            TaskModelAssembler taskModelAssembler
    ) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectModelAssembler = projectModelAssembler;
        this.taskModelAssembler = taskModelAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(
            @PathVariable("id") String id
    ) {
        try {
            Project project = projectService.getProjectById(id);
            return ResponseEntity.ok(projectModelAssembler.toModel(project));
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
            return ProjectExceptionHandlers.handleProjectNotFoundException(e);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(
                projectModelAssembler.toCollectionModel(projects)
        );
    }

    @PostMapping("/")
    public ResponseEntity<?> createOne(
            @RequestBody Project project
    ) {
        try {
            Project savedProject = projectService.createNewProject(project);
            return ResponseEntity
                    .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProjectController.class).getOne(savedProject.getId())).toUri())
                    .body(projectModelAssembler.toModel(savedProject));
        } catch (ProjectMissingAttributeException e) {
            e.printStackTrace();
            return ProjectExceptionHandlers.handleProjectMissingAttributes(e);
        }
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<?> startWorking(
            @PathVariable("id") String id
    ) {
        try {
            Project project = projectService.startWorking(id);
            return ResponseEntity.ok(projectModelAssembler.toModel(project));
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
            return ProjectExceptionHandlers.handleProjectNotFoundException(e);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return SecurityExceptionHandlers.handleAccessDeniedException(e);
        } catch (ProjectStateException e) {
            e.printStackTrace();
            return ProjectExceptionHandlers.handleProjectStateException(e);
        }
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<?> stopWorking(
            @PathVariable("id") String id
    ) {
        try {
            Project project = projectService.stopWorking(id);
            return ResponseEntity.ok(projectModelAssembler.toModel(project));
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
            return ProjectExceptionHandlers.handleProjectNotFoundException(e);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            return SecurityExceptionHandlers.handleAccessDeniedException(e);
        } catch (ProjectStateException e) {
            e.printStackTrace();
            return ProjectExceptionHandlers.handleProjectStateException(e);
        }
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<?> getAllTasks(
            @PathVariable("id") String id
    ) {
        List<Task> tasks = taskService.getAllTasksForProjectId(id);
        return ResponseEntity.ok(
                taskModelAssembler.toCollectionModel(tasks)
        );
    }

}
