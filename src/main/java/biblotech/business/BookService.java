package biblotech.business;

import biblotech.dto.BookResponse;
import biblotech.dto.CreateBook;
import biblotech.entity.Book;
import biblotech.exceptions.BookDuplicationError;
import biblotech.exceptions.BookNotFound;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import biblotech.persistence.BookRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static biblotech.mapper.BookMapper.mapToBook;

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
        if (getBookByISBN(newBook.getBookIsbn()).isPresent()) {
            throw new BookDuplicationError("Book with ISBN " + newBook.getBookIsbn() + " already exists");
        }
        newBook =  bookRepository.insert(newBook);
        return newBook;

    }

    public BookResponse getBookByTitleAndAuthor(String title,String author) {
        var book = bookRepository.findByTitleAndAuthor(title, author);
        return book.map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with title: "+ title+ " and author: "+ author + " not found"));

        }


    public BookResponse getBookById(Long id) {
       return bookRepository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with id " + id + " not found"));
    }

    public Optional<Book> getBookByISBN(String bookISBN) {
        return bookRepository.findByISBN(bookISBN);
    }

    public BookResponse getOneBookByISBN(String bookISBN){
        return getBookByISBN(bookISBN)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with ISBN " + bookISBN + " not found"));

    }

}
