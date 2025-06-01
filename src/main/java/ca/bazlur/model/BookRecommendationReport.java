package ca.bazlur.model;

import com.embabel.agent.domain.library.HasContent;
import com.embabel.agent.domain.library.InternetResource;
import com.embabel.agent.domain.library.InternetResources;
import com.embabel.common.ai.prompt.PromptContribution;
import com.embabel.common.ai.prompt.PromptContributionLocation;
import com.embabel.common.ai.prompt.PromptContributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record BookRecommendationReport(String personalizedSummary, List<BookRecommendation> topRecommendations,
                                       Map<BookGenre, Double> genreAffinityScores,
                                       List<ReadingList> curatedReadingLists, List<String> identifiedReadingPatterns,
                                       Map<String, String> authorRecommendations, List<String> expandHorizonsGenres,
                                       LocalDate nextRecommendationUpdate,
                                       List<InternetResource> links) implements HasContent, InternetResources {

    @Override
    public String getContent() {
        return String.format(
                "Personalized Summary: %s\nTop Recommendations: %d books\nReading Lists: %d curated lists\nNew Genres to Explore: %s",
                personalizedSummary, topRecommendations.size(), curatedReadingLists.size(),
                String.join(", ", expandHorizonsGenres)
        );
    }

    @NotNull
    @Override
    public List<InternetResource> getLinks() {
        return links;
    }
}