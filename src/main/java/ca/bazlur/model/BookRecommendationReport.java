package ca.bazlur.model;

import com.embabel.agent.domain.library.HasContent;
import com.embabel.agent.domain.library.InternetResource;
import com.embabel.agent.domain.library.InternetResources;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonClassDescription("A comprehensive report containing personalized book recommendations and related information")
public record BookRecommendationReport(
    @JsonPropertyDescription("A personalized summary of the user's reading profile and recommendations") 
    String personalizedSummary,

    @JsonPropertyDescription("List of top book recommendations tailored to the user") 
    List<BookRecommendation> topRecommendations,

    @JsonPropertyDescription("Map of book genres to affinity scores indicating user's preference level for each genre") 
    Map<BookGenre, Double> genreAffinityScores,

    @JsonPropertyDescription("List of curated reading lists tailored to the user's preferences") 
    List<ReadingList> curatedReadingLists,

    @JsonPropertyDescription("List of identified reading patterns and habits") 
    List<String> identifiedReadingPatterns,

    @JsonPropertyDescription("Map of author names to recommendation reasons") 
    Map<String, String> authorRecommendations,

    @JsonPropertyDescription("List of genres suggested for the user to explore beyond their comfort zone") 
    List<String> expandHorizonsGenres,

    @JsonPropertyDescription("Date when recommendations should be updated next") 
    LocalDate nextRecommendationUpdate,

    @JsonPropertyDescription("List of relevant internet resources related to the recommendations") 
    List<InternetResource> links
) implements HasContent, InternetResources {

     public BookRecommendationReport {
         // Initialize null collections to empty collections
         topRecommendations = topRecommendations != null ? topRecommendations : new ArrayList<>();
         genreAffinityScores = genreAffinityScores != null ? genreAffinityScores : new HashMap<>();
         curatedReadingLists = curatedReadingLists != null ? curatedReadingLists : new ArrayList<>();
         identifiedReadingPatterns = identifiedReadingPatterns != null ? identifiedReadingPatterns : new ArrayList<>();
         authorRecommendations = authorRecommendations != null ? authorRecommendations : new HashMap<>();
         expandHorizonsGenres = expandHorizonsGenres != null ? expandHorizonsGenres : new ArrayList<>();
         links = links != null ? links : new ArrayList<>();
         personalizedSummary = personalizedSummary != null ? personalizedSummary : "";
     }

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
