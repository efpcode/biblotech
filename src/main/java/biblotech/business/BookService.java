package biblotech.business;

import biblotech.dto.*;
import biblotech.entity.Book;
import biblotech.exceptions.BookDuplicationError;
import biblotech.exceptions.BookNotFound;
import biblotech.mapper.BookMapper;
import biblotech.mapper.SortedBookOrderMapper;
import biblotech.mapper.SortedBookQueryMapper;
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
        BookPagination bookPagination = BookPagination.valueOf(pageNumber, pageSize);
        String[] expectedQuery = {"author","title"};
        String[] expectedOrder = {"asc","desc"};
        SortedBookQuery sortedBookQuery = SortedBookQueryMapper.mapToSortedBookQuery(new SortedBookQuery(sortBy), expectedQuery);
        SortedBookOrder sortedBookOrder = SortedBookOrderMapper.mapToOrderType(new SortedBookOrder(sortOrder), expectedOrder);

        SortedBookByAuthorAndTitleParamsDTO bookParams = new SortedBookByAuthorAndTitleParamsDTO(
                bookPagination.pageNumber(),
                bookPagination.pageSize(),
                sortedBookQuery.sortBy(),
                sortedBookOrder.order());

        var patternAuthor = "%" + author + "%";

        PageRequest pageRequest = PageRequest.ofPage(bookParams.pageNumber(), bookParams.pageSize(), true);

        if(bookParams.sortOrder().equals("asc")) {
            bookOrder = Order.by(Sort.asc(bookParams.sortBy()));

        }else {
            bookOrder = Order.by(Sort.desc(bookParams.sortBy()));
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



}
