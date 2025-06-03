package ca.bazlur.util;

import ca.bazlur.model.Book;
import ca.bazlur.model.BookGenre;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for loading and managing book data.
 */
public class BookUtil {
    private static final Logger logger = LoggerFactory.getLogger(BookUtil.class);
    private static final String BOOKS_JSON_FILE = "books.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Loads books from the JSON file.
     *
     * @return A list of Book objects
     */
    public static List<Book> loadBooks() {
        try {
            ClassPathResource resource = new ClassPathResource(BOOKS_JSON_FILE);
            try (InputStream inputStream = resource.getInputStream()) {
                List<Map<String, Object>> booksData = objectMapper.readValue(inputStream, new TypeReference<>() {});
                return convertToBooks(booksData);
            }
        } catch (IOException e) {
            logger.error("Error loading books from JSON file", e);
            return getFallbackBooks();
        }
    }
    
    /**
     * Converts the raw data from JSON to Book objects.
     *
     * @param booksData The raw data from the JSON file
     * @return A list of Book objects
     */
    private static List<Book> convertToBooks(List<Map<String, Object>> booksData) {
        List<Book> books = new ArrayList<>();
        
        for (Map<String, Object> bookData : booksData) {
            try {
                String title = (String) bookData.get("title");
                String author = (String) bookData.get("author");
                BookGenre genre = BookGenre.valueOf((String) bookData.get("genre"));
                String isbn = (String) bookData.get("isbn");
                int pageCount = ((Number) bookData.get("pageCount")).intValue();
                String description = (String) bookData.get("description");
                double averageRating = ((Number) bookData.get("averageRating")).doubleValue();
                
                books.add(new Book(title, author, genre, isbn, pageCount, description, averageRating));
            } catch (Exception e) {
                logger.warn("Error converting book data: {}", bookData, e);
            }
        }
        
        return books;
    }
    
    /**
     * Provides a fallback list of books in case the JSON file cannot be loaded.
     *
     * @return A list of Book objects
     */
    private static List<Book> getFallbackBooks() {
        List<Book> books = new ArrayList<>();
        
        // Add a few fallback books
        books.add(new Book(
                "The Midnight Library",
                "Matt Haig",
                BookGenre.FICTION,
                "9780525559474",
                304,
                "Between life and death there is a library, and within that library, the shelves go on forever.",
                4.5
        ));
        
        books.add(new Book(
                "Project Hail Mary",
                "Andy Weir",
                BookGenre.SCIENCE_FICTION,
                "9780593135204",
                496,
                "A lone astronaut must save the earth from disaster.",
                4.8
        ));
        
        return books;
    }
}