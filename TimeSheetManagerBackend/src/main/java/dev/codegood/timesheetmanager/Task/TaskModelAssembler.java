package dev.codegood.timesheetmanager.Task;

import dev.codegood.timesheetmanager.project.ProjectController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {
    @Override
    public EntityModel<Task> toModel(Task task) {
        return EntityModel.of(
                task,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskController.class).getOneById(task.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TaskController.class).deleteTask(task.getId())).withRel("delete"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProjectController.class).getOne(task.getProjectId())).withRel("project")
        );
    }
}
