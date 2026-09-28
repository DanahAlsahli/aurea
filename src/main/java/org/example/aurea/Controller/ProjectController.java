package org.example.aurea.Controller;

import org.example.aurea.Model.Project;
import org.example.aurea.Service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody Project project) {
        return ResponseEntity.status(201).body(projectService.addProject(project));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.status(200).body(projectService.getAllProjects());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Integer id) {

        Project project = projectService.getProjectById(id);

        if (project == null) {
            return ResponseEntity.status(404).body("Project not found");
        }

        return ResponseEntity.status(200).body(project);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(
            @PathVariable Integer id,
            @RequestBody Project project) {

        boolean updated = projectService.updateProject(id, project);

        if (!updated) {
            return ResponseEntity.status(404).body("Project not found");
        }

        return ResponseEntity.status(200).body("Project updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id) {

        boolean deleted = projectService.deleteProject(id);

        if (!deleted) {
            return ResponseEntity.status(404).body("Project not found");
        }

        return ResponseEntity.status(200).body("Project deleted successfully");
    }
}
