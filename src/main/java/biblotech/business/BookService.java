package biblotech.business;

import biblotech.dto.BookResponse;
import biblotech.dto.CreateBook;
import biblotech.entity.Book;
import biblotech.rules.ValidBookAuthor;
import biblotech.rules.ValidBookTitle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import biblotech.persistence.BookRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static biblotech.mapper.BookMapper.mapToBook;
import static biblotech.mapper.BookMapper.mapToBookResponse;

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

    public BookResponse getBookByTitleAndAuthor(String title,String author) {
        var book = bookRepository.findByTitleAndAuthor(title, author);
        return book.map(BookResponse::new)
                .orElseThrow(() -> new RuntimeException("Book with title: "+ title+ " and author: "+ author + " not found"));

        }


    public BookResponse getBookById(Long id) {
       return bookRepository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new RuntimeException("Book with id " + id + " not found"));
    }

}
