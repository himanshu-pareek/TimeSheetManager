package dev.codegood.timesheetmanager.project;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    public List<Project> findAllByCreatedById(String createdById);
}
