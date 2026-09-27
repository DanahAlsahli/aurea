package org.example.aurea.Controller;

import org.example.aurea.Model.Recommendation;
import org.example.aurea.Service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRecommendation(
            @RequestBody Recommendation recommendation) {

        return ResponseEntity
                .status(201)
                .body(recommendationService.addRecommendation(recommendation));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllRecommendations() {

        return ResponseEntity
                .status(200)
                .body(recommendationService.getAllRecommendations());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRecommendationById(
            @PathVariable Integer id) {

        Recommendation recommendation =
                recommendationService.getRecommendationById(id);

        if (recommendation == null) {
            return ResponseEntity
                    .status(404)
                    .body("Recommendation not found");
        }

        return ResponseEntity.status(200).body(recommendation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRecommendation(
            @PathVariable Integer id,
            @RequestBody Recommendation recommendation) {

        boolean updated =
                recommendationService.updateRecommendation(id, recommendation);

        if (!updated) {
            return ResponseEntity
                    .status(404)
                    .body("Recommendation not found");
        }

        return ResponseEntity
                .status(200)
                .body("Recommendation updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecommendation(
            @PathVariable Integer id) {

        boolean deleted =
                recommendationService.deleteRecommendation(id);

        if (!deleted) {
            return ResponseEntity
                    .status(404)
                    .body("Recommendation not found");
        }

        return ResponseEntity
                .status(200)
                .body("Recommendation deleted successfully");
    }
}
