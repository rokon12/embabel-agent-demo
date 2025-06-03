package ca.bazlur.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Represents a book in the system.
 */
@JsonClassDescription("A book with metadata including title, author, genre, and rating information")
public record Book(
    @JsonPropertyDescription("The title of the book") 
    String title,

    @JsonPropertyDescription("The author of the book") 
    String author,

    @JsonPropertyDescription("The genre category of the book") 
    BookGenre genre,

    @JsonPropertyDescription("The ISBN (International Standard Book Number) identifier") 
    String isbn,

    @JsonPropertyDescription("The total number of pages in the book") 
    int pageCount,

    @JsonPropertyDescription("A brief summary or description of the book's content") 
    String description,

    @JsonPropertyDescription("The average rating of the book (typically on a scale of 0-5)") 
    double averageRating
) {
    /**
     * Creates a book with all fields.
     */
    public Book {
        // Validate required fields
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }
        if (genre == null) {
            throw new IllegalArgumentException("Genre cannot be null");
        }
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        if (pageCount <= 0) {
            throw new IllegalArgumentException("Page count must be positive");
        }
    }

    /**
     * Creates a book with minimal required fields and default values for others.
     */
    public Book(String title, String author, BookGenre genre, String isbn) {
        this(title, author, genre, isbn, 100, "", 0.0);
    }

}
