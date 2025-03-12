package business;

import dto.BookResponse;
import dto.CreateBook;
import entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import persistence.BookRepository;

import java.util.List;
import java.util.Objects;

import static mapper.BookMapper.mapToBook;

@ApplicationScoped
public class BookService {
    private BookRepository bookRepository;

    public BookService() {

    }

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .map(BookResponse::new)
                .filter(Objects::nonNull)
                .toList();
    }


    public Book createBook(CreateBook book) {
        var newBook = mapToBook(book);
        newBook =  bookRepository.insert(newBook);
        return newBook;

    }


    public BookResponse getBookById(Long id) {
       return bookRepository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new RuntimeException("Book with id " + id + " not found"));
    }
}
