package business;

import dto.BookResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import persistence.BookRepository;

import java.util.List;
import java.util.Objects;

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
}
