package ca.bazlur.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("A book recommendation with detailed information about why it was recommended")
public record BookRecommendation(
    @JsonPropertyDescription("The title of the recommended book") 
    String title,

    @JsonPropertyDescription("The author of the recommended book") 
    String author,

    @JsonPropertyDescription("The genre category of the recommended book") 
    BookGenre genre,

    @JsonPropertyDescription("The ISBN (International Standard Book Number) identifier") 
    String isbn,

    @JsonPropertyDescription("The rating of the book (typically on a scale of 0-5)") 
    double rating,

    @JsonPropertyDescription("A brief summary or description of the book's content") 
    String description,

    @JsonPropertyDescription("The specific reason why this book was recommended to the user") 
    String reasonForRecommendation,

    @JsonPropertyDescription("The confidence level of this recommendation") 
    RecommendationConfidence confidence,

    @JsonPropertyDescription("List of similar books the user has already read") 
    List<String> similarBooksRead,

    @JsonPropertyDescription("Estimated time in hours it would take to read this book") 
    int estimatedReadingTimeHours
) {
}
