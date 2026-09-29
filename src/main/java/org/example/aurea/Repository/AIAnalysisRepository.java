package org.example.aurea.Repository;

import org.example.aurea.Model.AIAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AIAnalysisRepository
        extends JpaRepository<AIAnalysis, Integer> {

    List<AIAnalysis> findByProjectIdOrderByCreatedAtDesc(
            Integer projectId
    );

    Optional<AIAnalysis> findFirstByProjectIdOrderByCreatedAtDesc(
            Integer projectId
    );

    List<AIAnalysis> findByProjectIdAndAnalysisType(
            Integer projectId,
            String analysisType
    );

    List<AIAnalysis> findByProjectIdAndRiskLevel(
            Integer projectId,
            String riskLevel
    );

    List<AIAnalysis> findByProjectIdAndScoreGreaterThanEqual(
            Integer projectId,
            Integer score
    );

    List<AIAnalysis> findByProjectIdAndScoreLessThan(
            Integer projectId,
            Integer score
    );

    List<AIAnalysis> findByProjectId(Integer projectId);
}

