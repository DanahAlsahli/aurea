package org.example.aurea.Service;

import org.example.aurea.Model.Studio;
import org.example.aurea.Repository.StudioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioService {

    private final StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    // Create
    public Studio addStudio(Studio studio) {
        return studioRepository.save(studio);
    }

    // Read All
    public List<Studio> getAllStudios() {
        return studioRepository.findAll();
    }

    // Read By ID
    public Studio getStudioById(Integer id) {
        return studioRepository.findById(id).orElse(null);
    }

    // Update
    public boolean updateStudio(Integer id, Studio studio) {

        Studio existingStudio = studioRepository.findById(id).orElse(null);

        if (existingStudio == null) {
            return false;
        }

        existingStudio.setProjectId(studio.getProjectId());
        existingStudio.setType(studio.getType());
        existingStudio.setStatus(studio.getStatus());

        studioRepository.save(existingStudio);

        return true;
    }

    // Delete
    public boolean deleteStudio(Integer id) {

        Studio existingStudio = studioRepository.findById(id).orElse(null);

        if (existingStudio == null) {
            return false;
        }

        studioRepository.delete(existingStudio);

        return true;
    }
}
