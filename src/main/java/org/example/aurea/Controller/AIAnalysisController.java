package org.example.aurea.Controller;

import org.example.aurea.Model.AIAnalysis;
import org.example.aurea.Service.AIAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai-analysis")
public class AIAnalysisController {

    private final AIAnalysisService aiAnalysisService;

    public AIAnalysisController(AIAnalysisService aiAnalysisService) {
        this.aiAnalysisService = aiAnalysisService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAnalysis(@RequestBody AIAnalysis analysis) {
        return ResponseEntity.status(201)
                .body(aiAnalysisService.addAnalysis(analysis));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllAnalyses() {
        return ResponseEntity.status(200)
                .body(aiAnalysisService.getAllAnalyses());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAnalysisById(@PathVariable Integer id) {

        AIAnalysis analysis = aiAnalysisService.getAnalysisById(id);

        if (analysis == null) {
            return ResponseEntity.status(404)
                    .body("AI Analysis not found");
        }

        return ResponseEntity.status(200).body(analysis);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAnalysis(
            @PathVariable Integer id,
            @RequestBody AIAnalysis analysis) {

        boolean updated =
                aiAnalysisService.updateAnalysis(id, analysis);

        if (!updated) {
            return ResponseEntity.status(404)
                    .body("AI Analysis not found");
        }

        return ResponseEntity.status(200)
                .body("AI Analysis updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnalysis(@PathVariable Integer id) {

        boolean deleted =
                aiAnalysisService.deleteAnalysis(id);

        if (!deleted) {
            return ResponseEntity.status(404)
                    .body("AI Analysis not found");
        }

        return ResponseEntity.status(200)
                .body("AI Analysis deleted successfully");
    }


    @PostMapping("/analyze/{projectId}")
    public ResponseEntity<?> analyzeProject(
            @PathVariable Integer projectId) {

        AIAnalysis analysis =
                aiAnalysisService.analyzeProject(projectId);

        return ResponseEntity.status(200)
                .body(analysis);
    }
}
