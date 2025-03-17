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

    public List<BookResponse> getBookByTitleAndAuthor(String title,String author) {
        var books = bookRepository.findByBookTitleAndBookAuthorIgnoreCase(title, author);
        if (books.isEmpty()) {
            throw new BookNotFound("Book with title " + title + " and author " + author + " not found");
        }
        return books.stream()
                .map(BookMapper::mapToBookResponse)
                .filter(Objects::nonNull)
                .toList();

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
        BookPagination bookPagination = BookPagination.Of(pageNumber, pageSize);
        SortedBookQuery sortedBookQuery = SortedBookQueryMapper.mapToSortedBookQuery(new SortedBookQuery(sortBy));
        SortedBookOrder sortedBookOrder = SortedBookOrderMapper.mapToOrderType(new SortedBookOrder(sortOrder));

        SortedBooksQueryParams bookParams = new SortedBooksQueryParams(
                bookPagination.pageNumber(),
                bookPagination.pageSize(),
                sortedBookQuery.sortBy(),
                sortedBookOrder.order());


        PageRequest pageRequest = PageRequest.ofPage(bookParams.pageNumber(), bookParams.pageSize(), true);

        if(bookParams.sortOrder().equals("asc")) {
            bookOrder = Order.by(Sort.asc(bookParams.sortBy()));

        }else {
            bookOrder = Order.by(Sort.desc(bookParams.sortBy()));
        }

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

            bookPage = bookRepository.findByBookAuthorLike(
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
            bookPage = bookRepository.findByBookByPage(pageRequest, bookOrder);

            bookPage.content().addAll(bookRepository.findByBookTitleAndBookAuthorIgnoreCase(title, author));
        }
        return bookPage;
    }

    private static List<BookResponse> getBookResponseList(Page<Book> booksByQuery) {
        return booksByQuery.stream()
                .map(BookMapper::mapToBookResponse)
                .filter(Objects::nonNull)
                .toList();
    }



}
