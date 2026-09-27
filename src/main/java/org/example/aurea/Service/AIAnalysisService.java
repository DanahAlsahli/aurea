package org.example.aurea.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.example.aurea.Model.AIAnalysis;
import org.example.aurea.Model.Project;
import org.example.aurea.Repository.AIAnalysisRepository;
import org.example.aurea.Repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIAnalysisService {

    private final AIAnalysisRepository aiAnalysisRepository;
    private final ProjectRepository projectRepository;
    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper;

    public AIAnalysisService(
            AIAnalysisRepository aiAnalysisRepository,
            ProjectRepository projectRepository) {

        this.aiAnalysisRepository = aiAnalysisRepository;
        this.projectRepository = projectRepository;

        this.openAIClient = OpenAIOkHttpClient.fromEnv();
        this.objectMapper = new ObjectMapper();
    }

    // =========================
    // Create
    // =========================
    public AIAnalysis addAnalysis(AIAnalysis analysis) {
        return aiAnalysisRepository.save(analysis);
    }

    // =========================
    // Read All
    // =========================
    public List<AIAnalysis> getAllAnalyses() {
        return aiAnalysisRepository.findAll();
    }

    // =========================
    // Read By ID
    // =========================
    public AIAnalysis getAnalysisById(Integer id) {
        return aiAnalysisRepository.findById(id).orElse(null);
    }

    // =========================
    // Update
    // =========================
    public boolean updateAnalysis(Integer id, AIAnalysis analysis) {

        AIAnalysis existingAnalysis =
                aiAnalysisRepository.findById(id).orElse(null);

        if (existingAnalysis == null) {
            return false;
        }

        existingAnalysis.setProjectId(analysis.getProjectId());
        existingAnalysis.setAnalysisType(analysis.getAnalysisType());
        existingAnalysis.setScore(analysis.getScore());
        existingAnalysis.setResult(analysis.getResult());
        existingAnalysis.setStrengths(analysis.getStrengths());
        existingAnalysis.setWeaknesses(analysis.getWeaknesses());
        existingAnalysis.setAiRecommendation(
                analysis.getAiRecommendation()
        );

        aiAnalysisRepository.save(existingAnalysis);

        return true;
    }

    // =========================
    // Delete
    // =========================
    public boolean deleteAnalysis(Integer id) {

        AIAnalysis existingAnalysis =
                aiAnalysisRepository.findById(id).orElse(null);

        if (existingAnalysis == null) {
            return false;
        }

        aiAnalysisRepository.delete(existingAnalysis);

        return true;
    }

    // =========================
    // AI Project Analysis
    // =========================
    public AIAnalysis analyzeProject(Integer projectId) {

        // 1. Get project from database
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found with id: " + projectId
                        )
                );

        // 2. Prepare project information
        String projectData = """
                Project Name: %s
                Description: %s
                Industry: %s
                Status: %s
                """.formatted(
                project.getName(),
                project.getDescription(),
                project.getIndustry(),
                project.getStatus()
        );

        // 3. Prepare AI prompt
        ChatCompletionCreateParams params =
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_4O_MINI)

                        .addSystemMessage("""
                                You are an AI business project analyst.

                                Analyze the project information provided by the user.

                                Return ONLY valid JSON.

                                Use exactly this structure:

                                {
                                  "overallAnalysis": "A concise overall analysis of the project",
                                  "score": 85,
                                  "strengths": "Main strengths of the project",
                                  "weaknesses": "Main weaknesses of the project",
                                  "recommendations": "Practical recommendations for improving the project"
                                }

                                Rules:
                                - score must be a number from 0 to 100.
                                - Do not use Markdown.
                                - Do not use code fences.
                                - Do not add any text outside the JSON.
                                - All fields must be included.
                                """)

                        .addUserMessage(projectData)
                        .build();

        // 4. Send request to OpenAI
        ChatCompletion response =
                openAIClient.chat()
                        .completions()
                        .create(params);

        // 5. Get AI response
        String aiResult = response.choices()
                .get(0)
                .message()
                .content()
                .orElse("");

        // 6. Create analysis object
        AIAnalysis analysis = new AIAnalysis();

        analysis.setProjectId(projectId);
        analysis.setAnalysisType("AI_PROJECT_ANALYSIS");

        // Save complete AI response
        analysis.setResult(aiResult);

        // 7. Parse JSON response
        try {

            JsonNode jsonNode = objectMapper.readTree(aiResult);

            // Score
            if (jsonNode.has("score") && !jsonNode.get("score").isNull()) {
                analysis.setScore(
                        jsonNode.get("score").asInt()
                );
            }

            // Strengths
            if (jsonNode.has("strengths")
                    && !jsonNode.get("strengths").isNull()) {

                analysis.setStrengths(
                        jsonNode.get("strengths").asText()
                );
            }

            // Weaknesses
            if (jsonNode.has("weaknesses")
                    && !jsonNode.get("weaknesses").isNull()) {

                analysis.setWeaknesses(
                        jsonNode.get("weaknesses").asText()
                );
            }

            // Recommendations
            if (jsonNode.has("recommendations")
                    && !jsonNode.get("recommendations").isNull()) {

                analysis.setAiRecommendation(
                        jsonNode.get("recommendations").asText()
                );
            }

        } catch (Exception e) {

            // If AI does not return valid JSON,
            // keep the complete response.
            analysis.setAiRecommendation(aiResult);
        }

        // 8. Save analysis in database
        return aiAnalysisRepository.save(analysis);
    }
}
