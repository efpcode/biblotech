package biblotech.business;

import biblotech.dto.*;
import biblotech.entity.Book;
import biblotech.exceptions.BookDuplicationError;
import biblotech.exceptions.BookNotFound;
import biblotech.exceptions.InvalidSortByQueryException;
import biblotech.exceptions.InvalidSortOrderQueryException;
import biblotech.mapper.BookMapper;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import biblotech.persistence.BookRepository;

import java.util.Arrays;
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
        var book = bookRepository.findByBookTitleAndBookAuthor(title, author);
        return book.map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with title: "+ title+ " and author: "+ author + " not found"));

        }


    public BookResponse getBookById(Long id) {
       return bookRepository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with id " + id + " not found"));
    }

    public Optional<Book> getBookByISBN(String bookISBN) {
        return bookRepository.findByBookISBN(bookISBN);
    }

    public BookResponse getOneBookByISBN(String bookISBN){
        return getBookByISBN(bookISBN)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with ISBN " + bookISBN + " not found"));

    }

    public SortedBookPageResponse getBooksByAuthor(String author, String sortBy, String sortOrder ,Long pageNumber, Integer pageSize) {
        Order<Book> bookOrder;

        SortedBookByAuthorAndTitleParamsDTO bookParams = new SortedBookByAuthorAndTitleParamsDTO(pageNumber, pageSize, sortBy, sortOrder);
        var bookParamsDTO = sortedBookParamsChecker(bookParams);

        var patternAuthor = "%" + author + "%";

        PageRequest pageRequest = PageRequest.ofPage(bookParamsDTO.pageNumber(), bookParamsDTO.pageSize(), true);

        if(bookParamsDTO.sortOrder().equals("asc")) {
            bookOrder = Order.by(Sort.asc(bookParamsDTO.sortBy()));

        }else {
            bookOrder = Order.by(Sort.desc(bookParamsDTO.sortBy()));
        }



        Page<Book> bookPage = bookRepository.findByBookAuthorLike(
                patternAuthor,
                pageRequest,
                bookOrder
                );

        List<BookResponse> bookResponses = bookPage.stream().map(BookMapper::mapToBookResponse)
                .filter(Objects::nonNull)
                .toList();

        BookListResponse bookListResponse = new BookListResponse(bookResponses);

        return new SortedBookPageResponse(bookListResponse, bookPage.numberOfElements(), bookPage.totalPages());

    }

    private SortedBookByAuthorAndTitleParamsDTO sortedBookParamsChecker(SortedBookByAuthorAndTitleParamsDTO sortedBookParams){
        if(!Arrays.asList("asc", "desc").contains(sortedBookParams.sortOrder())) {
            throw new InvalidSortOrderQueryException("Invalid sort order expected asc or desc");
        }
        if(!Arrays.asList("bookTitle", "bookAuthor").contains(sortedBookParams.sortBy())) {
            throw new InvalidSortByQueryException("Invalid sort by expected title or author");
        }
        return sortedBookParams;

    }

}
