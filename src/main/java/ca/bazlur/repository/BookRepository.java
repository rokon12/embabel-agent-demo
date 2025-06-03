package ca.bazlur.repository;

import ca.bazlur.model.Book;
import ca.bazlur.model.BookGenre;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    Optional<Book> findByIsbn(String isbn);
    
    List<Book> findByTitle(String title);
    
    List<Book> findByAuthor(String author);

    List<Book> findByGenre(BookGenre genre);

    List<Book> findAll();
    

    Book save(Book book);
}