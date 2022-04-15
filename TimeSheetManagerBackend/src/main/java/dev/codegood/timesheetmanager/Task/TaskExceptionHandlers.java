package dev.codegood.timesheetmanager.Task;

import dev.codegood.timesheetmanager.Task.exceptions.TaskNotFoundException;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TaskExceptionHandlers {
    public static ResponseEntity<?> handleTaskNotFoundException(
            TaskNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(
                        Problem.create()
                                .withTitle("Resource not found")
                                .withDetail(e.getMessage())
                                .withStatus(HttpStatus.NOT_FOUND)
                );
    }
}
