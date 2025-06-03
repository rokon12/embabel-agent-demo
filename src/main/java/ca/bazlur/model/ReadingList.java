package ca.bazlur.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("A curated list of book recommendations organized around a theme or purpose")
public record ReadingList(
    @JsonPropertyDescription("The name or title of the reading list") 
    String listName,

    @JsonPropertyDescription("A description of the reading list's theme or purpose") 
    String description,

    @JsonPropertyDescription("The list of book recommendations included in this reading list") 
    List<BookRecommendation> books,

    @JsonPropertyDescription("The target reading mood this list is designed for") 
    ReadingMood targetMood,

    @JsonPropertyDescription("Estimated number of weeks to complete all books in this list") 
    int estimatedCompletionWeeks
) {
}
