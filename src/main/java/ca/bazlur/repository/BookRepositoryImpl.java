package ca.bazlur.repository;

import ca.bazlur.model.Book;
import ca.bazlur.model.BookGenre;
import ca.bazlur.util.BookUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of BookRepository that stores books in memory.
 * Books are loaded from a JSON file using the BookUtil class.
 */
@Component
public class BookRepositoryImpl implements BookRepository {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositoryImpl.class);
    private final Map<String, Book> books = new HashMap<>();

    /**
     * Constructor that initializes the repository with books loaded from the JSON file.
     */
    public BookRepositoryImpl() {
        loadBooksFromFile();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public List<Book> findByTitle(String title) {
        if (title == null || title.isBlank()) {
            return List.of();
        }

        String titleLowerCase = title.toLowerCase();
        return books.values().stream()
                .filter(book -> book.title().toLowerCase().contains(titleLowerCase))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        if (author == null || author.isBlank()) {
            return List.of();
        }

        String authorLowerCase = author.toLowerCase();
        return books.values().stream()
                .filter(book -> book.author().toLowerCase().contains(authorLowerCase))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByGenre(BookGenre genre) {
        if (genre == null) {
            return List.of();
        }

        return books.values().stream()
                .filter(book -> book.genre() == genre)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book save(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        books.put(book.isbn(), book);
        return book;
    }

    private void loadBooksFromFile() {
        logger.info("Loading books from file");
        List<Book> bookList = BookUtil.loadBooks();

        for (Book book : bookList) {
            books.put(book.isbn(), book);
        }

        logger.info("Loaded {} books from file", books.size());
    }
}
