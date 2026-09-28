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

    public Studio addStudio(Studio studio) {
        return studioRepository.save(studio);
    }

    public List<Studio> getAllStudios() {
        return studioRepository.findAll();
    }

    public Studio getStudioById(Integer id) {
        return studioRepository.findById(id).orElse(null);
    }

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

    public boolean deleteStudio(Integer id) {

        Studio existingStudio = studioRepository.findById(id).orElse(null);

        if (existingStudio == null) {
            return false;
        }

        studioRepository.delete(existingStudio);

        return true;
    }
}
