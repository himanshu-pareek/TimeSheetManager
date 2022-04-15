package dev.codegood.timesheetmanager.task;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.Date;

@Document("task")
public class Task {
    @Id
    private String id;
    @Indexed
    private String projectId;
    private String userId;
    private Date startDate;
    private Date endDate;

    public Task() {}

    public Task(String userId, String projectId, Date startDate, Date endDate) {
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isValid() {
        if (this.projectId == null) return false;
        if (this.startDate == null) return false;
        if (this.endDate == null) return false;
        this.projectId = this.projectId.trim();
        if (this.projectId.isBlank()) return false;
        return this.startDate.compareTo(this.endDate) < 0;
    }
}
