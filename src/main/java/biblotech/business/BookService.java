package biblotech.business;

import biblotech.dto.*;
import biblotech.entity.Book;
import biblotech.exceptions.BookDuplicationError;
import biblotech.exceptions.BookNotFound;
import biblotech.exceptions.InvalidSearchQuery;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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





    public Book createBook(CreateBook book) {
        var newBook = mapToBook(book);
        if (getBookByISBN(newBook.getBookIsbn()).isPresent()) {
            throw new BookDuplicationError("Book with ISBN " + newBook.getBookIsbn() + " already exists");
        }
        newBook =  bookRepository.insert(newBook);
        return newBook;

    }

    public SortedBookPageResponse getBookBySearchQuery(
            String title,
            String author,
            Long pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder,
            String startDate,
            String endDate) {
        Page<Book> bookPage;

        SortedBooksQueryParams bookParams = getSortedBooksQueryParams(pageNumber, pageSize, sortBy, sortOrder);
        PageRequest pageRequest = PageRequest.ofPage(bookParams.pageNumber(), bookParams.pageSize(), true);
        Order<Book> bookOrder = getSortsDirection(bookParams);

        if(startDate != null && endDate != null) {
            var parsedStarDate = LocalDate.parse(startDate);
            var parsedEndDate = LocalDate.parse(endDate);

            bookPage = bookRepository.findBookPublishDateBetween(parsedStarDate, parsedEndDate, pageRequest, bookOrder);
        }
        else {
            bookPage = bookRepository.findBookTitleAndBookAuthorIgnoreCasePage(title, author, pageRequest, bookOrder);
        }
        if (!bookPage.hasContent()) {
            throw new BookNotFound("Book was not found!");
        }

        List<BookResponse> booksListResponses = getBookResponseList(bookPage);

        return new SortedBookPageResponse(new BookListResponse(booksListResponses), bookPage.numberOfElements(), bookPage.totalPages());


        }


    public BookResponse getBookById(Long id) {
       return bookRepository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with id " + id + " not found"));
    }

    public Optional<Book> getBookByISBN(String bookISBN) {
        return bookRepository.findByBookIsbn(bookISBN);
    }

    public BookResponse getOneBookByISBN(String bookISBN){
        return getBookByISBN(bookISBN)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with ISBN " + bookISBN + " not found"));

    }

    public SortedBookPageResponse getBooksSorted(String author, String title, String sortBy, String sortOrder , Long pageNumber, Integer pageSize) {
        Order<Book> bookOrder;
        Page<Book> bookPage;

        SortedBooksQueryParams bookParams = getSortedBooksQueryParams(pageNumber, pageSize, sortBy, sortOrder);


        PageRequest pageRequest = PageRequest.ofPage(bookParams.pageNumber(), bookParams.pageSize(), true);

        bookOrder = getSortsDirection(bookParams);

        bookPage = getBooksPages(author, title, pageRequest, bookOrder);

        List<BookResponse> bookResponses = getBookResponseList(bookPage);
        if(bookResponses.isEmpty()) {
            throw new BookNotFound("No books found for search with: "+ author+ " and "+title);
        }

        BookListResponse bookListResponse = new BookListResponse(bookResponses);

        return new SortedBookPageResponse(bookListResponse, bookPage.numberOfElements(), bookPage.totalPages());

    }

    // Helper methods

    private Page<Book> getBooksPages(String author, String title, PageRequest pageRequest, Order<Book> bookOrder) {
        Page<Book> bookPage;
        if(author ==null && title ==null) {
            bookPage = bookRepository.findAll(pageRequest, bookOrder);
        }
        else if(title==null) {

            bookPage = bookRepository.findBookAuthorLike(
                    author,
                    pageRequest,
                    bookOrder
            );
        } else if (author == null) {
            bookPage = bookRepository.findBookTitleLike(
                    title,
                    pageRequest,
                    bookOrder);

        }else{
            throw new InvalidSearchQuery("Invalid search query for root endpoint please use: api/books/search");

        }
        return bookPage;
    }

    private static List<BookResponse> getBookResponseList(Page<Book> booksByQuery) {
        return booksByQuery.stream()
                .map(BookMapper::mapToBookResponse)
                .filter(Objects::nonNull)
                .toList();
    }
    private static SortedBooksQueryParams getSortedBooksQueryParams(Long pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        BookPagination bookPagination = BookPagination.Of(pageNumber, pageSize);
        String sorted = SortedBookQueryMapper.mapToSortedBookQuery(sortBy);
        String order = SortedBookOrderMapper.mapToOrderType(sortOrder);
        return new SortedBooksQueryParams(bookPagination.pageNumber(), bookPagination.pageSize(), sorted, order);
    }

    private static Order<Book> getSortsDirection(SortedBooksQueryParams bookParams) {
        return bookParams.sortOrder().equals("asc") ? Order.by(Sort.asc(bookParams.sortBy())) : Order.by(Sort.desc(bookParams.sortBy()));
    }

}
