package ca.bazlur;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the book recommendation engine.
 * Contains settings for the LLM model used for recommendations.
 */
@ConfigurationProperties(prefix = "book.recommendation")
public class BookRecommendationProperties {

    /**
     * The LLM model to use for generating book recommendations.
     */
    private String recommendationLlm = "gpt-4o";

    public String getRecommendationLlm() {
        return recommendationLlm;
    }

    public void setRecommendationLlm(String recommendationLlm) {
        this.recommendationLlm = recommendationLlm;
    }
}
