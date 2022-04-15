package dev.codegood.timesheetmanager.project;

import dev.codegood.timesheetmanager.project.exception.ProjectMissingAttributeException;
import dev.codegood.timesheetmanager.project.exception.ProjectNotFoundException;
import dev.codegood.timesheetmanager.project.exception.ProjectStateException;
import org.apache.coyote.Response;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProjectExceptionHandlers {
    public static ResponseEntity<?> handleProjectNotFoundException(
            ProjectNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Resource not found")
                        .withDetail(e.getMessage())
                        .withStatus(HttpStatus.NOT_FOUND)
                );
    }

    public static ResponseEntity<?> handleProjectMissingAttributes(
            ProjectMissingAttributeException e
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Bad request")
                        .withDetail(e.getMessage())
                        .withStatus(HttpStatus.BAD_REQUEST)
                );
    }

    public static ResponseEntity<?> handleProjectStateException(
            ProjectStateException e
    ) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail(e.getMessage())
                        .withStatus(HttpStatus.METHOD_NOT_ALLOWED));
    }
}
