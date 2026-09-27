package org.example.aurea.Service;

import org.example.aurea.Model.Project;
import org.example.aurea.Repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Integer id) {
        return projectRepository.findById(id).orElse(null);
    }

    public boolean updateProject(Integer id, Project project) {

        Project oldProject = projectRepository.findById(id).orElse(null);

        if (oldProject == null) {
            return false;
        }

        oldProject.setUserId(project.getUserId());
        oldProject.setName(project.getName());
        oldProject.setDescription(project.getDescription());
        oldProject.setIndustry(project.getIndustry());
        oldProject.setStatus(project.getStatus());

        projectRepository.save(oldProject);

        return true;
    }

    public boolean deleteProject(Integer id) {

        Project project = projectRepository.findById(id).orElse(null);

        if (project == null) {
            return false;
        }

        projectRepository.delete(project);

        return true;
    }
}