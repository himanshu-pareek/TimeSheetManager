package dev.codegood.timesheetmanager.project;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {

    @Override
    public EntityModel<Project> toModel(Project project) {
        EntityModel<Project> projectEntityModel = EntityModel.of(
                project,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProjectController.class).getOne(project.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProjectController.class).getAllTasks(project.getId())).withRel("tasks")
        );

        if (project.getStartedAt() == null) {
            // Link to start working on project
            projectEntityModel.add(
                    WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(
                                    ProjectController.class
                            ).startWorking(project.getId())
                    ).withRel("start_work")
            );
        } else {
            projectEntityModel.add(
                    WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(
                                    ProjectController.class
                            ).stopWorking(project.getId())
                    ).withRel("stop_work")
            );
        }

        return projectEntityModel;
    }
}
