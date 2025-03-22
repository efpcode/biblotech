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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static biblotech.mapper.BookMapper.fromUpdateOrPatchBook;
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





    // Crud Sessions

    // Insert
    public Book createBook(CreateBook book) {
        var newBook = mapToBook(book);
        if (getBookByISBN(newBook.getBookIsbn()).isPresent() || getBookAuthorAndTitle(newBook).isPresent() ) {
            throw new BookDuplicationError(
                    "Book with: " + newBook.getBookIsbn() + " " + newBook.getBookAuthor() + " "+ newBook.getBookTitle() +" already exists");
        }
        newBook =  bookRepository.insert(newBook);
        return newBook;
    }
    // Update

    public Book updateBook(UpdateBook bookUpdate, Long id) {
        var book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFound("Book with id: " + id + " was not found in database");
        }
        var updateBook = fromUpdateOrPatchBook(book.get(), bookUpdate);
        bookRepository.update(updateBook);
        return updateBook;

    }

    // Patch

    public Book patchBook(PatchBook bookPatch, Long id) {
        var book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFound("Book with id: " + id + " was not found in database");
        }
        var bookPatched = fromUpdateOrPatchBook(book.get(), bookPatch);

        bookRepository.update(bookPatched);
        return bookPatched;
    }

    // Delete

    public void deleteBook(Long id) {
        var book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFound("Book with id: " + id + " was not found in database");
        }
        bookRepository.delete(book.get());


    }




    // General Getter methods

    public Optional<Book> getBookAuthorAndTitle(Book book){
        return bookRepository.getBookTitleAndBookAuthor(book.getBookTitle(), book.getBookAuthor());
    }


    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with id " + id + " not found"));
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book with id " + id + " not found"));
    }

    public Optional<Book> getBookByISBN(String bookISBN) {
        return bookRepository.findByBookIsbn(bookISBN);
    }

    public BookResponse getOneBookByISBN(String bookISBN){
        return getBookByISBN(bookISBN)
                .map(BookResponse::new)
                .orElseThrow(() -> new BookNotFound("Book with ISBN " + bookISBN + " not found"));

    }

    // Search and Filtering by Author, Title and Date Ranges.

    public SortedBookPageResponse getBookBySearchQuery(BookFilterQueryResponse searchQuery) {

        SortedBooksQueryParams bookParams = getSortedBooksQueryParams(
                searchQuery.getPageNumber(), searchQuery.getPageSize(),
                searchQuery.getSortBy(),
                searchQuery.getSortOrder()
        );
        PageRequest pageRequest = PageRequest.ofPage(bookParams.pageNumber(), bookParams.pageSize(), true);
        Order<Book> bookOrder = getSortsDirection(bookParams);

        Page<Book> bookPage = getBooksQueryFilter(
                searchQuery.getTitle(),
                searchQuery.getAuthor(),
                searchQuery.getStartDate(),
                searchQuery.getEndDate(),
                pageRequest, bookOrder
        );
        if(!bookPage.hasContent()){
            throw new  BookNotFound("No books found!");
        }else {
            List<BookResponse> booksListResponses = getBookResponseList(bookPage);
            return new SortedBookPageResponse(new BookListResponse(booksListResponses), bookPage.numberOfElements(), bookPage.totalPages());
        }

    }






    // General Search for with sort capabilities.

    public SortedBookPageResponse getBooksSorted(BookAuthorsQueryResponse searchSort) {
        Order<Book> bookOrder;
        Page<Book> bookPage;

        SortedBooksQueryParams bookParams = getSortedBooksQueryParams(
                searchSort.getPageNumber(),
                searchSort.getPageSize(),
                searchSort.getSortBy(),
                searchSort.getSortOrder()
        );


        PageRequest pageRequest = PageRequest.ofPage(bookParams.pageNumber(), bookParams.pageSize(), true);

        bookOrder = getSortsDirection(bookParams);

        bookPage = getBooksPages(searchSort.getAuthor(), searchSort.getTitle(), pageRequest, bookOrder);

        List<BookResponse> bookResponses = getBookResponseList(bookPage);
        if(bookResponses.isEmpty()) {
            throw new BookNotFound("No books found for search with: "+ searchSort.getAuthor()+ " and "+searchSort.getTitle());
        }

        BookListResponse bookListResponse = new BookListResponse(bookResponses);

        return new SortedBookPageResponse(bookListResponse, bookPage.numberOfElements(), bookPage.totalPages());

    }



    // Helper methods

    private Page<Book> getBooksPages(String author, String title, PageRequest pageRequest, Order<Book> bookOrder) {
        Page<Book> bookPage = null;
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

    private Page<Book> getBooksQueryFilter(String title, String author, String startDate, String endDate, PageRequest pageRequest, Order<Book> bookOrder) {
        Page<Book> bookPage;
        var parsedStarDate = Objects.isNull(startDate)? null: LocalDate.parse(startDate);
        var parsedEndDate = Objects.isNull(endDate)? null: LocalDate.parse(endDate);

        if(startDate != null && endDate != null && title != null && author != null) {
            bookPage = bookRepository.findBookPublishDateBetweenAndBookAuthorAndBookTitle(author, title, parsedStarDate, parsedEndDate, pageRequest, bookOrder);
            return bookPage;

        } else if (startDate != null && endDate != null && title != null) {
            bookPage = bookRepository.findBookPublishDateBetweenAndBookTitle(title, parsedStarDate, parsedEndDate, pageRequest, bookOrder);
            return bookPage;

        }else if (startDate != null && endDate != null && author != null) {
            bookPage = bookRepository.findBookPublishDateBetweenAndBookAuthor(author, parsedStarDate, parsedEndDate, pageRequest, bookOrder);
            return bookPage;
        }

        else if(startDate != null && endDate != null) {

            bookPage = bookRepository.findBookPublishDateBetween(parsedStarDate, parsedEndDate, pageRequest, bookOrder);
            return bookPage;
        }
        else if(author != null && title != null)  {
            bookPage = bookRepository.findBookTitleAndBookAuthorIgnoreCasePage(title, author, pageRequest, bookOrder);
            return bookPage;
        }else{
            throw new InvalidSearchQuery("Filtered Search Error:\n\n" +
                    "Invalid search query for current endpoint please use: api/books/ for single search " +
                    "\n Minimum parameters to pass without null values for filtered search is\n\t author:"+ author +  " title: " + title+ "\n\tOR\n\t" +
                    "\tendDate: " + parsedStarDate + " and startDate: " + parsedEndDate);
        }


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
