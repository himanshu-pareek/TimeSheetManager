package dev.codegood.timesheetmanager.project.exception;

public class ProjectMissingAttributeException extends Exception {
    public ProjectMissingAttributeException() {
        super();
    }

    public ProjectMissingAttributeException(String missingAttribute) {
        this(missingAttribute, null);
    }

    public ProjectMissingAttributeException(String missingAttribute, Throwable cause) {
        super(String.format("Missing required attribute - %s", missingAttribute), cause);
    }

    public ProjectMissingAttributeException(Throwable cause) {
        super(cause);
    }
}
