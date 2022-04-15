package dev.codegood.timesheetmanager.project;

import dev.codegood.timesheetmanager.Task.Task;
import dev.codegood.timesheetmanager.project.exception.ProjectStateException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("project")
public class Project {
    @Id
    private String id;
    private String name;
    private String description;
    private String externalUrl;
    @Indexed
    private String createdById;
    private long timeSpent;
    private Date startedAt;

    public Project() {}

    public Project(
            String name,
            String description,
            String externalUrl
    ) {
        this.name = name;
        this.description = description;
        this.externalUrl = externalUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getCreatedBy() {
        return createdById;
    }

    public void setCreatedBy(String createdBy) {
        this.createdById = createdBy;
    }

    public void startWorking() throws ProjectStateException {
        if (startedAt != null) {
            // Already in progress
            throw new ProjectStateException("Project already in progress.");
        }
        startedAt = new Date();
    }

    public Task stopWorking() throws ProjectStateException {
        if (startedAt == null) {
            throw new ProjectStateException("Project is not in progress");
        }
        Date now = new Date();
        timeSpent += now.getTime() - startedAt.getTime();
        Task task = new Task(null, id, startedAt, now);
        startedAt = null;
        return task;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void removeTask(Task task) {
        long timeSpentToRemove = task.getEndDate().getTime() - task.getStartDate().getTime();
        timeSpent -= timeSpentToRemove;
    }
}
