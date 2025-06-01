package ca.bazlur.model;

import java.util.List;

public record ReadingList(String listName, String description, List<BookRecommendation> books, ReadingMood targetMood,
                   int estimatedCompletionWeeks) {
}
