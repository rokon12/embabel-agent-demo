package ca.bazlur.model;

import com.embabel.common.ai.prompt.PromptContribution;
import com.embabel.common.ai.prompt.PromptContributionLocation;
import com.embabel.common.ai.prompt.PromptContributor;
import com.embabel.ux.form.Text;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.jetbrains.annotations.NotNull;

/**
 * Represents user preferences for book recommendations.
 * This is used for collecting user input via forms.
 */
@JsonClassDescription("User preferences for book recommendations")
public record UserPreferences(
    @Text(label = "Favorite genre") 
    @JsonPropertyDescription("User's favorite book genre") 
    String favoriteGenre,

    @Text(label = "Current reading mood") 
    @JsonPropertyDescription("User's current reading mood") 
    String currentMood,

    @Text(label = "Available reading time (hours per week)") 
    @JsonPropertyDescription("User's available reading time in hours per week") 
    String readingTimePerWeek,

    @Text(label = "Specific author interest") 
    @JsonPropertyDescription("Specific author the user is interested in") 
    String authorInterest
) implements PromptContributor {
    @JsonCreator
    public UserPreferences(
        @JsonProperty("favoriteGenre") String favoriteGenre,
        @JsonProperty("currentMood") String currentMood,
        @JsonProperty("readingTimePerWeek") String readingTimePerWeek,
        @JsonProperty("authorInterest") String authorInterest
    ) {
        this.favoriteGenre = favoriteGenre;
        this.currentMood = currentMood;
        this.readingTimePerWeek = readingTimePerWeek;
        this.authorInterest = authorInterest;
    }

    @Override
    public String getRole() {
        return "user_preferences";
    }

    @NotNull
    @Override
    public PromptContributionLocation getPromptContributionLocation() {
        return PromptContributionLocation.END;
    }

    @NotNull
    @Override
    public PromptContribution promptContribution() {
        return new PromptContribution(getContent(), getPromptContributionLocation(), getRole());
    }

    private String getContent() {
        return String.format(
                "User Reading Preferences:\n" +
                "- Favorite Genre: %s\n" +
                "- Current Reading Mood: %s\n" +
                "- Available Reading Time: %s hours per week\n" +
                "- Author Interest: %s",
                favoriteGenre,
                currentMood,
                readingTimePerWeek,
                authorInterest
        );
    }

    @NotNull
    @Override
    public String contribution() {
        return getContent();
    }
}
