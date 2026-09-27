package org.example.aurea.Api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIResult {

    private Integer score;

    private String strengths;

    private String weaknesses;

    private String recommendation;
}
