package org.example.aurea.Repository;

import org.example.aurea.Model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository
        extends JpaRepository<Recommendation, Integer> {
}
