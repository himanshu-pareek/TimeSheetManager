package dev.codegood.timesheetmanager.project;

import dev.codegood.timesheetmanager.Task.Task;
import dev.codegood.timesheetmanager.Task.TaskService;
import dev.codegood.timesheetmanager.project.exception.ProjectMissingAttributeException;
import dev.codegood.timesheetmanager.project.exception.ProjectNotFoundException;
import dev.codegood.timesheetmanager.project.exception.ProjectStateException;
import dev.codegood.timesheetmanager.util.AuthUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    public ProjectService(ProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    public List <Project> getAllProjects() {
        return this.getAllProjects(AuthUtil.getUserId());
    }

    public List<Project> getAllProjects(String userId) {
        return projectRepository.findAllByCreatedById(userId);
    }

    public Project getProjectById(String id) throws ProjectNotFoundException {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            return optionalProject.get();
        }
        throw new ProjectNotFoundException("Project with id = " + id + " not found");
    }

    public Project createNewProject(Project model) throws ProjectMissingAttributeException {
        Project project = new Project(
                model.getName(),
                model.getDescription(),
                model.getExternalUrl()
        );
        project.setCreatedBy(AuthUtil.getUserId());

        if (project.getName() == null || project.getName().isBlank()) throw new ProjectMissingAttributeException("name");
        project.setName(project.getName().trim());
        if (project.getDescription() != null) {
            project.setDescription(project.getDescription().trim());
        }
        if (project.getExternalUrl() != null) {
            project.setExternalUrl(project.getExternalUrl().trim());
        }
        return projectRepository.save(project);
    }

    public Project startWorking(String id) throws ProjectNotFoundException, AccessDeniedException, ProjectStateException {
        Project project = getProjectById(id);
        if (!project.getCreatedBy().equals(AuthUtil.getUserId())) {
            throw new AccessDeniedException("Access denied.");
        }
        project.startWorking();
        projectRepository.save(project);
        return project;
    }

    public Project stopWorking(String id) throws ProjectNotFoundException, AccessDeniedException, ProjectStateException {
        Project project = getProjectById(id);
        if (!project.getCreatedBy().equals(AuthUtil.getUserId())) {
            throw new AccessDeniedException("Access denied.");
        }
        Task task = project.stopWorking();
        task.setUserId(AuthUtil.getUserId());
        projectRepository.save(project);
        taskService.createTask(task);
        return project;
    }
}
