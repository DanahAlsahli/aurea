package org.example.aurea.Service;

import org.example.aurea.Model.Recommendation;
import org.example.aurea.Repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public Recommendation addRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    public Recommendation getRecommendationById(Integer id) {
        return recommendationRepository.findById(id).orElse(null);
    }

    public boolean updateRecommendation(
            Integer id,
            Recommendation newRecommendation) {

        Recommendation existing =
                recommendationRepository.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }

        existing.setAnalysisId(newRecommendation.getAnalysisId());
        existing.setTitle(newRecommendation.getTitle());
        existing.setDescription(newRecommendation.getDescription());
        existing.setPriority(newRecommendation.getPriority());
        existing.setStatus(newRecommendation.getStatus());

        recommendationRepository.save(existing);

        return true;
    }

    public boolean deleteRecommendation(Integer id) {

        Recommendation existing =
                recommendationRepository.findById(id).orElse(null);

        if (existing == null) {
            return false;
        }

        recommendationRepository.delete(existing);

        return true;
    }
}
