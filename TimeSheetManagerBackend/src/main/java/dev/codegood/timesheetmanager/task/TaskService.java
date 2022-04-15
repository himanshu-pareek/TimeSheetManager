package dev.codegood.timesheetmanager.task;

import dev.codegood.timesheetmanager.task.exceptions.TaskInvalidException;
import dev.codegood.timesheetmanager.task.exceptions.TaskNotFoundException;
import dev.codegood.timesheetmanager.project.Project;
import dev.codegood.timesheetmanager.project.ProjectRepository;
import dev.codegood.timesheetmanager.project.exception.ProjectNotFoundException;
import dev.codegood.timesheetmanager.util.AuthUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public void saveTask (Task task) {
        taskRepository.save(task);
    }

    public Task createTask (Task task) throws TaskInvalidException, ProjectNotFoundException {
        if (!task.isValid()) {
            throw new TaskInvalidException("Task is not valid");
        }
        task.setUserId(AuthUtil.getUserId());
        Project project = getProjectById(task.getProjectId());
        project.addTask(task);

        projectRepository.save(project);
        taskRepository.save(task);
        return task;
    }

    public Task getTaskById (String id) throws TaskNotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException(String.format("Task with id = %s not found", id));
        }
        return optionalTask.get();
    }

    public List<Task> getAllTasksForProjectId (String projectId) {
        return taskRepository.findAllByProjectId(projectId);
    }

    public Project getProjectById(String id) throws ProjectNotFoundException {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            return optionalProject.get();
        }
        throw new ProjectNotFoundException("Project with id = " + id + " not found");
    }

    Task deleteTask (String id) throws TaskNotFoundException, ProjectNotFoundException {
        Task task = getTaskById(id);
        if (!task.getUserId().equals(AuthUtil.getUserId())) {
            throw new AccessDeniedException("You are not allowed to delete this task");
        }
        Project project = getProjectById(task.getProjectId());
        project.removeTask (task);
        projectRepository.save(project);
        taskRepository.delete(task);
        return task;
    }
}
