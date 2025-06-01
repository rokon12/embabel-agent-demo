package ca.bazlur.model;

import java.util.List;

public record BookRecommendation(String title, String author, BookGenre genre, String isbn, double rating, String description,
                          String reasonForRecommendation, RecommendationConfidence confidence,
                          List<String> similarBooksRead, int estimatedReadingTimeHours) {
}
