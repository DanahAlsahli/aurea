package org.example.aurea.Repository;

import org.example.aurea.Model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository
        extends JpaRepository<Recommendation, Integer> {

    List<Recommendation> findByAnalysisId(Integer analysisId);

    List<Recommendation> findByAnalysisIdOrderByIdDesc(
            Integer analysisId
    );

    long countByAnalysisId(Integer analysisId);
    List<Recommendation> findByAnalysisIdAndPriority(
            Integer analysisId,
            String priority
    );

    List<Recommendation> findByAnalysisIdAndStatus(
            Integer analysisId,
            String status
    );

    List<Recommendation> findByAnalysisIdAndImpact(
            Integer analysisId,
            String impact
    );

    List<Recommendation> findByAnalysisIdAndProgressLessThan(
            Integer analysisId,
            Integer progress
    );

    List<Recommendation> findByAnalysisIdAndProgressGreaterThanEqual(
            Integer analysisId,
            Integer progress
    );
}


