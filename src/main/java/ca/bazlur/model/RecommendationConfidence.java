package ca.bazlur.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;

@JsonClassDescription("Confidence levels for book recommendations indicating how well they match user preferences")
public enum RecommendationConfidence {
    LOW, MEDIUM, HIGH, VERY_HIGH
}
