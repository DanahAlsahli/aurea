package org.example.aurea.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer analysisId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String priority;

    private String status;

    public Recommendation() {
    }

    public Recommendation(Integer analysisId, String title,
                          String description, String priority,
                          String status) {
        this.analysisId = analysisId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
