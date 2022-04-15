package dev.codegood.timesheetmanager.project.exception;

public class ProjectStateException extends Exception {
    public ProjectStateException() {
        super();
    }

    public ProjectStateException(String message) {
        super(message);
    }

    public ProjectStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectStateException(Throwable cause) {
        super(cause);
    }
}
