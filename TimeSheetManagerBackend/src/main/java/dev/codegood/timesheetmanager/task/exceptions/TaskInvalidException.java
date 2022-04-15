package dev.codegood.timesheetmanager.task.exceptions;

public class TaskInvalidException extends Exception {
    public TaskInvalidException() {
        super();
    }

    public TaskInvalidException(String message) {
        super(message);
    }

    public TaskInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskInvalidException(Throwable cause) {
        super(cause);
    }
}
