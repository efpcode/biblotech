package biblotech.business;


import biblotech.dto.*;
import biblotech.entity.Book;
import biblotech.exceptions.BookDuplicationError;
import biblotech.exceptions.BookNotFound;
import biblotech.exceptions.InvalidBookPage;
import biblotech.exceptions.InvalidSearchQuery;
import biblotech.mapper.BookMapper;
import biblotech.persistence.BookRepository;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private Book book;


    @Mock
    private
    BookRepository bookRepository;

    @Mock
    private Page<Book> bookPage;

    @Mock
    private List<BookResponse> bookResponses;

    @Mock
    private BookAuthorsQueryResponse searchSort;

    @Mock
    private BookFilterQueryResponse searchFilter;

    @Mock
    private SortedBooksQueryParams sortedBooksQueryParams;

    @Mock
    private SortedBookPageResponse sortedBookPageResponse;

    @Mock
    private Order<Book> bookOrder;

    @Mock
    private BookMapper bookMapper;


    @InjectMocks
    private BookService bookService;

    static List<Book> books() {
        List<Book> booksList = new ArrayList<>();

        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        Book book4 = new Book();
        Book book5 = new Book();
        Book book6 = new Book();

        book1.setBookID(1L);
        book2.setBookID(2L);
        book3.setBookID(3L);
        book4.setBookID(4L);
        book5.setBookID(5L);
        book6.setBookID(6L);

        book1.setBookAuthor("Books Author101");
        book2.setBookAuthor("Book Author201");
        book3.setBookAuthor("Book Author301");
        book4.setBookAuthor("Book Author401");
        book5.setBookAuthor("Book Author501");
        book6.setBookAuthor("Books Author601");

        book1.setBookTitle("Books Title1");
        book2.setBookTitle("Book Title10");
        book3.setBookTitle("Book Title100");
        book4.setBookTitle("Book Title500");
        book5.setBookTitle("Book Title111");
        book6.setBookTitle("Books Title103");

        book1.setBookIsbn("0307278443");
        book2.setBookIsbn("9780307278445");
        book3.setBookIsbn("0140449132");
        book4.setBookIsbn("9780140449136");
        book5.setBookIsbn("0061120081");
        book6.setBookIsbn("9780061120080");

        book1.setBookDescription("Book Description");
        book2.setBookDescription("Book Description");
        book3.setBookDescription("Book Description");
        book4.setBookDescription("Book Description");
        book5.setBookDescription("Book Description");
        book6.setBookDescription("Book Description");

        book1.setBookPublishDate(LocalDate.of(1916, 1, 1));
        book2.setBookPublishDate(LocalDate.of(1926, 1, 1));
        book3.setBookPublishDate(LocalDate.of(1936, 1, 1));
        book4.setBookPublishDate(LocalDate.of(1946, 1, 1));
        book5.setBookPublishDate(LocalDate.of(1956, 1, 1));
        book6.setBookPublishDate(LocalDate.of(1966, 1, 1));

        book1.setBookPagesNumber(101L);
        book2.setBookPagesNumber(201L);
        book3.setBookPagesNumber(301L);
        book4.setBookPagesNumber(401L);
        book5.setBookPagesNumber(501L);
        book6.setBookPagesNumber(601L);

        booksList.add(book1);
        booksList.add(book2);
        booksList.add(book3);
        booksList.add(book4);
        booksList.add(book5);
        booksList.add(book6);

        return booksList;

    }

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setBookID(1L);
        book.setBookAuthor("Book Author");
        book.setBookTitle("Book Title1");
        book.setBookIsbn("0307278443");
        book.setBookDescription("Book Description");
        book.setBookPublishDate(LocalDate.of(1956, 1, 1));
        book.setBookPagesNumber(101L);
    }

    @Test
    @DisplayName("Default Constructor Should Create BookService Instance")
    void defaultConstructorShouldCreateInstance() {
        BookService bookService = new BookService();

        assertNotNull(bookService);
    }


    @Nested
    class FindBookByISBNOrIDTestSuite {

        @Test
        @DisplayName("BookResponse when fetched by Id with getBookById")
        void bookResponseWhenFetchedByIdWithGetBookById() {

            when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

            BookResponse bookResponse = bookService.getBookById(1L);
            assertNotNull(bookResponse);
            assertEquals(1L, bookResponse.id());
        }

        @Test
        @DisplayName("Book is fetched when getBook is called with correct id ")
        void bookIsFetchedWhenGetBookIsCalledWithCorrectId() {
            when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
            Book bookTest = bookService.getBook(1L);
            assertNotNull(bookTest);
            assertThat(bookTest.getBookID()).isEqualTo(1L);
            assertThat(bookTest.getBookAuthor()).isEqualTo("Book Author");
            assertThat(bookTest.getBookTitle()).isEqualTo("Book Title1");
            assertThat(bookTest.getBookIsbn()).isEqualTo("0307278443");
            assertThat(bookTest.getBookDescription()).isEqualTo("Book Description");
            assertThat(bookTest.getBookPublishDate()).isEqualTo(LocalDate.of(1956, 1, 1));
            assertThat(bookTest.getBookPagesNumber()).isEqualTo(101L);

        }


        @Test
        @DisplayName("Book when fetched by isbn number getBookByISBN")
        void bookWhenFetchedByIsbnNumberGetBookByIsbn() {

            when(bookRepository.findByBookIsbn("0307278443")).thenReturn(Optional.of(book));

            Optional<Book> bookResponse = bookService.getBookByISBN("0307278443");
            assertEquals("0307278443", bookResponse.get().getBookIsbn());
        }


        @Test
        @DisplayName("Incorrect Id throws exception book not found")
        void incorrectIdThrowsExceptionBookNotFound() {
            when(bookRepository.findById(8L)).thenThrow(BookNotFound.class).thenReturn(Optional.empty());
            assertThrows(BookNotFound.class, () -> bookService.getBookById(8L));
        }

        @Test
        @DisplayName("Incorrect Id throws exception when calling getBook")
        void incorrectIdThrowsExceptionWhenCallingGetBook() {
            when(bookRepository.findById(8L)).thenThrow(BookNotFound.class).thenReturn(Optional.of(book));
            assertThrows(BookNotFound.class, () -> bookService.getBook(8L));

        }

        @Test
        @DisplayName("Incorrect isbn throws exception book not found")
        void incorrectIsbnThrowsExceptionBookNotFound() {

            when(bookRepository.findByBookIsbn("9780306406157")).thenReturn(Optional.empty());
            assertThrows(BookNotFound.class, () -> bookService.getOneBookByISBN("9780306406157"));

        }


    }

    @Nested
    class FindBookByAuthorOrTitleTestSuite {
        @Test
        @DisplayName("Calling getBookAuthorAndTitle returns a book ")
        void callingGetBookAuthorAndTitleReturnsABook() {

            when(bookRepository.getBookTitleAndBookAuthor(eq("Book Title10"), eq("Book Author201"))).thenReturn(Optional.of(books().get(1)));
            var bookNew = bookService.getBookAuthorAndTitle(books().get(1));

            assertThat(bookNew).contains(books().get(1));

            Mockito.verify(bookRepository, times(1))
                    .getBookTitleAndBookAuthor(eq("Book Title10"), eq("Book Author201"));
        }

        @Test
        @DisplayName("getBookAuthorAndTitle null book returns optional empty")
        void getBookAuthorAndTitleNullBookReturnsOptionalEmpty() {

            var result = bookService.getBookAuthorAndTitle(null);
            assertThat(result).isEmpty();

            verify(bookRepository, never()).getBookTitleAndBookAuthor(anyString(), anyString());
        }


        @Test
        public void testGetBooksPages_withFindAll() {
            // Arrange
            List<Book> mockBooks = books();

            // Create a mocked Page object
            Page<Book> mockBookPage = Mockito.mock(Page.class);

            // Mock the behavior of Page
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(2L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(5);

            // Mock the repository's findAll method
            Mockito.when(bookRepository.findAll(Mockito.any(PageRequest.class), Mockito.any(Order.class)))
                    .thenReturn(mockBookPage);

            // Act
            PageRequest pageRequest = PageRequest.ofPage(2, 5, true); // Example page request
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle")); // Example sort order
            Page<Book> result = bookService.getBooksPages(null, null, pageRequest, bookOrder);

            // Assert
            Assertions.assertNotNull(result); // Ensure a valid Page is returned
            Assertions.assertEquals(5, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(2);
            assertThat(result.content()).isEqualTo(mockBooks);
            Mockito.verify(bookRepository, Mockito.times(1))
                    .findAll(
                            Mockito.any(PageRequest.class),
                            Mockito.any(Order.class)); // Ensure findAll was called
        }


        @Test
        @DisplayName("testGetBookPages_findBookAuthorLike")
        void testGetBookPagesFindBookAuthorLike() {
            List<Book> mockBooks = List.of(books().get(1), books().get(5));
            Page<Book> mockBookPage = Mockito.mock(Page.class);

            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(2);

            Mockito.when(bookRepository.findBookAuthorLike(
                            eq("ks"), Mockito.any(PageRequest.class),
                            Mockito.any(Order.class)))
                    .thenReturn(mockBookPage);

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookAuthor"));

            Page<Book> result = bookService.getBooksPages("ks", null, pageRequest, bookOrder);
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(1);
            assertThat(result.content()).isEqualTo(mockBooks);

            Mockito.verify(
                            bookRepository,
                            Mockito.times(1))
                    .findBookAuthorLike(eq("ks"),
                            Mockito.any(PageRequest.class),
                            Mockito.any(Order.class));

        }


        @Test
        @DisplayName("testGetBookPages_findBookTitleLike")
        void testGetBookPagesFindBookTitleLike() {
            List<Book> mockBooks = List.of(books().get(1), books().get(5));
            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(2);

            Mockito.when(bookRepository.findBookTitleLike(
                            eq("oks"), Mockito.any(PageRequest.class),
                            Mockito.any(Order.class))).
                    thenReturn(mockBookPage);

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));
            Page<Book> result = bookService.getBooksPages(null, "oks", pageRequest, bookOrder);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(1);
            assertThat(result.content()).isEqualTo(mockBooks);
            Mockito.verify(bookRepository, Mockito.times(1))
                    .findBookTitleLike(
                            eq("oks"),
                            Mockito.any(PageRequest.class),
                            Mockito.any(Order.class));


        }


        @Test
        @DisplayName("testGetBooKPages_throwsInvalidEndPointSearch")
        void testGetBooKPagesThrowsInvalidEndPointSearch() {
            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));


            assertThrows(InvalidSearchQuery.class, () -> bookService.getBooksPages("", "", pageRequest, bookOrder));
        }

        @Test
        @DisplayName("testGetBookPages_Invalid Or Corrupt Page")
        void testGetBookPagesInvalidOrCorruptPage() {

            assertThrows(InvalidBookPage.class, () -> {
                bookService.getBooksPages(null, null, null, null);
            }).getMessage().matches("Page corrupted found!: null");
        }

        @Test
        @DisplayName("testGetBooks_findAll Null From Repository throws exception ")
        void testGetBooksFindAllNullFromRepositoryThrowsException() {

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Mockito.when(bookRepository.findAll(Mockito.any(PageRequest.class), Mockito.any(Order.class))).thenReturn(null);

            assertThrows(InvalidBookPage.class, () -> bookService.getBooksPages(null, null, pageRequest, bookOrder))
                    .getMessage().matches("Page corrupted found!: null");


        }


        @Test
        @DisplayName("testGetBooks_findBookAuthorLike Null From repository throws exception")
        void testGetBooksFindBookAuthorLikeNullFromRepositoryThrowsException() {

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Mockito.when(bookRepository.findBookAuthorLike(eq("Author"), Mockito.any(PageRequest.class), Mockito.any(Order.class))).thenReturn(null);

            assertThrows(InvalidBookPage.class, () -> bookService.getBooksPages("Author", null, pageRequest, bookOrder))
                    .getMessage().matches("Page corrupted found!: null");

        }

        @Test
        @DisplayName("testGetBooks_findBookTitleLike null from repository throws exception")
        void testGetBooksFindBookTitleLikeNullFromRepositoryThrowsException() {

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Mockito.when(bookRepository.findBookTitleLike(eq("title"), Mockito.any(PageRequest.class), Mockito.any(Order.class))).thenReturn(null);

            assertThrows(InvalidBookPage.class, () -> bookService.getBooksPages(null, "title", pageRequest, bookOrder))
                    .getMessage().matches("Page corrupted found!: null");

        }

    }


    @Nested
    class FindBookByFilterQueriesTestSuite {

        @Test
        @DisplayName("getBooksQueryFilter_TwoParameterMust be passed as minimum or exception")
        void getBooksQueryFilterTwoParameterMustBePassedAsMinimumOrException() {

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            assertThrows(InvalidSearchQuery.class, () -> {
                bookService.getBooksQueryFilter(
                        null,
                        null,
                        null,
                        "2025-02-01",
                        pageRequest,
                        bookOrder
                );

            }).getMessage().matches("api/books/");
        }

        @Test
        @DisplayName("getBooksQueryFilter is PageBook null result in exception")
        void getBooksQueryFilterIsPageBooksNullResultInException() {


            Mockito.when(bookRepository.findBookPublishDateBetween(
                    any(LocalDate.class),
                    any(LocalDate.class),
                    any(PageRequest.class),
                    any(Order.class)
            )).thenReturn(null);

            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));
            assertThrows(InvalidBookPage.class, () -> {
                bookService.getBooksQueryFilter(null, null, "1990-01-01", "1992-02-01", pageRequest, bookOrder);
            });

        }


        @Test
        @DisplayName("getBooksQueryFilter_PassingOnlyAuthorAndTitle")
        void getBooksQueryFilterPassingOnlyAuthorAndTitle() {

            List<Book> mockBooks = List.of(books().get(1));
            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(1);

            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));


            Mockito.when(bookRepository.findBookTitleAndBookAuthorIgnoreCasePage(
                    eq("Title10"),
                    eq("201"),
                    any(PageRequest.class),
                    any(Order.class)
            )).thenReturn(mockBookPage);

            Page<Book> result = bookService.getBooksQueryFilter(
                    "Title10",
                    "201",
                    null,
                    null,
                    pageRequest,
                    bookOrder
            );

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(1);
            assertThat(result.content()).isEqualTo(mockBooks);
            Mockito.verify(bookRepository, Mockito.times(1))
                    .findBookTitleAndBookAuthorIgnoreCasePage(
                            eq("Title10"),
                            eq("201"),
                            any(PageRequest.class),
                            any(Order.class)
                    );


        }


        @Test
        @DisplayName("getBooksQueryFilter Passing Only Start and End date")
        void getBooksQueryFilterPassingOnlyStartAndEndDate() {

            List<Book> mockBooks = List.of(books().get(1));
            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(1);
            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Mockito.when(bookRepository.findBookPublishDateBetween(
                    eq(LocalDate.parse("1916-01-01")),
                    eq(LocalDate.parse("1936-01-01")),
                    any(PageRequest.class),
                    any(Order.class)
            )).thenReturn(mockBookPage);

            Page<Book> result = bookService.getBooksQueryFilter(null, null, "1916-01-01", "1936-01-01", pageRequest, bookOrder);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(1);
            assertThat(result.content()).isEqualTo(mockBooks);
            Mockito.verify(bookRepository, Mockito.times(1)).findBookPublishDateBetween(
                    eq(LocalDate.parse("1916-01-01")),
                    eq(LocalDate.parse("1936-01-01")),
                    any(PageRequest.class),
                    any(Order.class)
            );

        }


        @Test
        @DisplayName("getBooksQueryFilter Passing start date, end date and author search")
        void getBooksQueryFilterPassingStartDateEndDateAndAuthorSearch() {
            List<Book> mockBooks = List.of(books().get(1));
            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(1);

            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Mockito.when(bookRepository
                    .findBookPublishDateBetweenAndBookAuthor(
                            eq("201"),
                            eq(LocalDate.parse("1916-01-17")),
                            eq(LocalDate.parse("1930-01-01")),
                            any(PageRequest.class),
                            any(Order.class)
                    )).thenReturn(mockBookPage);

            Page<Book> result = bookService.getBooksQueryFilter(null, "201", "1916-01-17", "1930-01-01", pageRequest, bookOrder);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(1L);
            assertThat(result.content()).isEqualTo(mockBooks);
            Mockito.verify(bookRepository, Mockito.times(1))
                    .findBookPublishDateBetweenAndBookAuthor(eq("201"), eq(LocalDate.parse("1916-01-17")), eq(LocalDate.parse("1930-01-01")), any(PageRequest.class), any(Order.class));

        }


        @Test
        @DisplayName("getBooksQueryFilter Passing Start date End date and title search")
        void getBooksQueryFilterPassingStartDateEndDateAndTitleSearch() {
            List<Book> mockBooks = List.of(books().get(1));

            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(1);

            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Mockito.when(bookRepository.findBookPublishDateBetweenAndBookTitle(
                    eq("Books"),
                    eq(LocalDate.parse("1900-01-01")),
                    eq(LocalDate.parse("1930-01-01")),
                    any(PageRequest.class),
                    any(Order.class)
            )).thenReturn(mockBookPage);

            Page<Book> result = bookService.getBooksQueryFilter("Books", null, "1900-01-01", "1930-01-01", pageRequest, bookOrder);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(1L);
            assertThat(result.content()).isEqualTo(mockBooks);
            verify(bookRepository, Mockito.times(1)).findBookPublishDateBetweenAndBookTitle(
                    eq("Books"),
                    eq(LocalDate.parse("1900-01-01")),
                    eq(LocalDate.parse("1930-01-01")),
                    any(PageRequest.class),
                    any(Order.class)
            );

        }

        @Test
        @DisplayName("getBooksQueryFilter Passing all parameters search")
        void getBooksQueryFilterPassingAllParametersSearch() {
            List<Book> mockBooks = List.of(books().get(1));
            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(1);
            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Mockito.when(bookRepository.findBookPublishDateBetweenAndBookAuthorAndBookTitle(
                    eq("Book Author201"),
                    eq("Book"),
                    eq(LocalDate.parse("1900-01-01")),
                    eq(LocalDate.parse("1930-01-01")),
                    any(PageRequest.class),
                    any(Order.class))).thenReturn(mockBookPage);

            Page<Book> result = bookService.getBooksQueryFilter(
                    "Book",
                    "Book Author201",
                    "1900-01-01",
                    "1930-01-01",
                    pageRequest,
                    bookOrder);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, result.numberOfElements());
            assertThat(result.totalPages()).isEqualTo(1L);
            assertThat(result.content()).isEqualTo(mockBooks);

            verify(bookRepository, Mockito.times(1))
                    .findBookPublishDateBetweenAndBookAuthorAndBookTitle(
                            eq("Book Author201"),
                            eq("Book"),
                            eq(LocalDate.parse("1900-01-01")),
                            eq(LocalDate.parse("1930-01-01")),
                            any(PageRequest.class),
                            any(Order.class)
                    );


        }


    }

    @Nested
    class HelperMethods {

        @Test
        @DisplayName("dateConversionValidator returns null if date is empty or null")
        void dateConversionValidatorReturnsNullIfDateIsEmptyOrNull() {

            assertThat(bookService.dateFormatConversionValidator("")).isNull();
            assertThat(bookService.dateFormatConversionValidator(null)).isNull();


        }

        @Test
        @DisplayName("dateConversionValidator return null if date is malformed")
        void dateConversionValidatorReturnNullIfDateIsMalformed() {
            assertThat(bookService.dateFormatConversionValidator("1900-01")).isNull();

        }

        @Test
        @DisplayName("dateConversionValidator return null when parsed incorrectly")
        void dateConversionValidatorReturnNullWhenParsedIncorrectly() {
            assertThat(bookService.dateFormatConversionValidator("1")).isNull();

        }

        @Test
        @DisplayName("dateConversionValidator return not null if date is processed")
        void dateConversionValidatorReturnNotNullIfDateIsProcessed() {
            assertThat(bookService.dateFormatConversionValidator("1900-01-01")).isNotNull();
        }

        @Test
        @DisplayName("Validate FakeSortedBookPageResponse Conversion")
        void testFakeSortedBookPageResponseMapping() {
            // Create mock data
            List<BookResponse> mockBookResponses = books().stream().map(BookResponse::new).collect(Collectors.toList());
            BookListResponse mockBookListResponse = new BookListResponse(mockBookResponses);

            // Create the fake test response
            FakeSortedBookPageResponse fakeResponse = FakeSortedBookPageResponse.createForTest(
                    mockBookListResponse,
                    mockBookResponses.size(),
                    1L
            );

            // Map to production response type
            SortedBookPageResponse productionResponse = FakeSortedBookPageResponse.maptoPageResponse(
                    fakeResponse.sortedBooks(),
                    fakeResponse.totalElements(),
                    fakeResponse.totalPages()
            );

            // Assertions
            assertNotNull(productionResponse);
            assertEquals(fakeResponse.sortedBooks(), productionResponse.sortedBooks());
            assertEquals(fakeResponse.totalElements(), productionResponse.totalElements());
            assertEquals(fakeResponse.totalPages(), productionResponse.totalPages());
        }


        @Test
        void testGetBookResponseList() {
            List<Book> mockBooks = List.of(books().get(0), books().get(1));
            Page<Book> mockBookPage = Mockito.mock(Page.class);

            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);

            List<BookResponse> result = bookService.getBookResponseList(mockBookPage);
            assertNotNull(result);
            assertEquals(mockBooks.size(), result.size());
            assertThat(result).isEqualTo(mockBooks.stream()
                    .map(BookMapper::mapToBookResponse)
                    .toList());
        }


    }

    @Nested
    class PageResponseSingleQuerySearch {

        @Test
        @DisplayName("getBooksSorted: Valid Inputs and Sorted Results")
        void getBooksSorted_WithValidInputs_ShouldReturnSortedBooks() {
            // Arrange
            BookAuthorsQueryResponse searchSort = new BookAuthorsQueryResponse();
            searchSort.setAuthor("Books Author101");
            searchSort.setTitle(null);
            searchSort.setPageNumber(1L);
            searchSort.setSortBy("title");
            searchSort.setSortOrder("asc");
            searchSort.setPageSize(1);

            // Mock repository response
            List<Book> mockBooks = List.of(books().get(0));
            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));
            Page<Book> mockBookPage = Mockito.mock(Page.class);

            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(mockBooks.size());
            Mockito.when(mockBookPage.hasContent()).thenReturn(true);

            Mockito.when(bookRepository.findBookAuthorLike(
                            eq("Books Author101"),
                            any(PageRequest.class),
                            any(Order.class)))
                    .thenReturn(mockBookPage);

            // Expected responses
            List<BookResponse> expectedBookResponses = mockBooks.stream()
                    .map(BookMapper::mapToBookResponse)
                    .toList();
            BookListResponse expectedBookListResponse = new BookListResponse(expectedBookResponses);

            // Act
            SortedBookPageResponse response = bookService.getBooksSorted(searchSort);

            // Assert
            assertNotNull(response);
            assertThat(response.sortedBooks().allBooks()).isEqualTo(expectedBookResponses);
            assertThat(response.totalPages()).isEqualTo(mockBookPage.totalPages());
            assertThat(response.totalElements()).isEqualTo(mockBookPage.numberOfElements());
            assertThat(response.sortedBooks()).isEqualTo(expectedBookListResponse);
        }


        @Test
        @DisplayName("Empty searchSort not found throws exception")
        void emptySearchSort_ShouldThrowException() {

            assertThrows(InvalidSearchQuery.class, () -> bookService.getBooksSorted(null)).getMessage().matches(
                    "Search Sort is null or empty"
            );
        }

    }

    @Nested
    class MultipleBookSearchQueries {

        @Test
        @DisplayName("Multiple Search Params Test")
        void multipleSearchParamsTest() {
            BookFilterQueryResponse searchFilter = new BookFilterQueryResponse();
            searchFilter.setAuthor("Books Author101");
            searchFilter.setTitle("Books Title101");
            searchFilter.setPageNumber(1L);
            searchFilter.setSortBy("title");
            searchFilter.setSortOrder("asc");
            searchFilter.setPageSize(1);
            searchFilter.setStartDate("1900-01-01");
            searchFilter.setEndDate("1960-01-01");

            List<Book> mockBooks = List.of(books().get(0));

            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder = Order.by(Sort.asc("bookTitle"));

            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(mockBooks.size());
            Mockito.when(mockBookPage.hasContent()).thenReturn(true);

            Mockito.when(bookRepository.findBookPublishDateBetweenAndBookAuthorAndBookTitle(

                    eq("Books Author101"),
                    eq("Books Title101"),
                    eq(LocalDate.parse("1900-01-01")),
                    eq(LocalDate.parse("1960-01-01")),
                    any(PageRequest.class),
                    any(Order.class)
            )).thenReturn(mockBookPage);

            List<BookResponse> expectedBookResponse = mockBooks.stream().map(BookMapper::mapToBookResponse).toList();

            BookListResponse expectedBookListResponse = new BookListResponse(expectedBookResponse);

            SortedBookPageResponse response = bookService.getBookBySearchQuery(searchFilter);
            assertNotNull(response);
            assertThat(response.sortedBooks().allBooks()).isEqualTo(expectedBookResponse);
            assertThat(response.totalPages()).isEqualTo(mockBookPage.totalPages());
            assertThat(response.totalElements()).isEqualTo(mockBookPage.numberOfElements());
            assertThat(response.sortedBooks()).isEqualTo(expectedBookListResponse);

        }


        @Test
        @DisplayName("Empty book throws exceptions")
        void emptyBookThrowsExceptions() {
            BookFilterQueryResponse searchFilter = new BookFilterQueryResponse();
            searchFilter.setAuthor("Books Author101");
            searchFilter.setPageNumber(1L);
            searchFilter.setSortBy("title");
            searchFilter.setSortOrder("asc");
            searchFilter.setPageSize(1);
            searchFilter.setStartDate("1900-01-01");
            searchFilter.setEndDate("1960-01-01");

            assertThrows(InvalidBookPage.class, () -> bookService.getBookBySearchQuery(searchFilter))
                    .getMessage()
                    .matches("Page corrupted found");

        }


    }


    @Nested
    class OneBookFetchTest {

        @Test
        @DisplayName("getOneBookByISBN return bookrespoonse if book found")
        void getOneBookByIsbnReturnBookrespoonseIfBookFound() {
            Book book = books().get(0);
            BookResponse expectedBookResponse = BookMapper.mapToBookResponse(book);

            when(bookRepository.findByBookIsbn(eq(book.getBookIsbn()))).thenReturn(Optional.of(book));
            var actual = bookService.getOneBookByISBN(book.getBookIsbn());

            assertThat(actual).isEqualTo(expectedBookResponse);
            assertThat(actual)
                    .extracting(
                            BookResponse::id,
                            BookResponse::isbn,
                            BookResponse::author,
                            BookResponse::title,
                            BookResponse::description,
                            BookResponse::pages,
                            BookResponse::publishedYear
                    )
                    .containsExactly(
                            expectedBookResponse.id(),
                            expectedBookResponse.isbn(),
                            expectedBookResponse.author(),
                            expectedBookResponse.title(),
                            expectedBookResponse.description(),
                            expectedBookResponse.pages(),
                            expectedBookResponse.publishedYear()
                    );

        }


        @Test
        @DisplayName("ValidIsbn but that is not present returns exception")
        void validIsbnButThatIsNotPresentReturnsException() {

            assertThatThrownBy(() -> bookService.getOneBookByISBN("0123456789"))
                    .isInstanceOf(BookNotFound.class).hasMessage("Book with ISBN 0123456789 not found");

        }

        @Test
        @DisplayName("Valid Id and present id returns book response")
        void validIdAndPresentIdReturnsBookResponse() {

            Book book = books().get(0);
            BookResponse expectedBookResponse = BookMapper.mapToBookResponse(book);

            Mockito.when(bookRepository.findById(eq(1L))).thenReturn(Optional.of(book));

            var actual = bookService.getBookById(1L);

            assertThat(actual).isEqualTo(expectedBookResponse);
            assertThat(actual).extracting(BookResponse::id, BookResponse::isbn).containsExactly(expectedBookResponse.id(), expectedBookResponse.isbn());

        }


        @Test
        @DisplayName("Invalid Id or Book not present returns exception")
        void invalidIdOrBookNotPresentReturnsException() {

            assertThatThrownBy(() -> bookService.getBookById(7L)).isInstanceOf(BookNotFound.class).hasMessage("Book with id 7 not found");

        }


    }


    @Nested
    class CRUDBookTests {

        @Test
        @DisplayName("Adding Book correctly returns book")
        void addingBookCorrectlyReturnsBook() {


            CreateBook createBook = new CreateBook(
                    "BookTitle1001",
                    "BookAuthor1001",
                    "0123456789",
                    "BookDescription1001",
                    "1900-01-01",
                    "1"
            );

            // Create another instance to simulate the inserted book
            Book insertedBook = new Book();
            insertedBook.setBookID(6L);
            insertedBook.setBookTitle("BookTitle1001");
            insertedBook.setBookAuthor("BookAuthor1001");
            insertedBook.setBookIsbn("0123456789");
            insertedBook.setBookDescription("BookDescription1001");
            insertedBook.setBookPagesNumber(1L);
            insertedBook.setBookPublishDate(LocalDate.parse("1900-01-01"));

            // Mock the mapping behavior
            var mappedBook = BookMapper.mapToBook(createBook);
            mappedBook.setBookID(insertedBook.getBookID());

            when(bookRepository.insert(any(Book.class))).thenReturn(mappedBook);


            // Act: Call the method under test
            Book result = bookService.createBook(createBook);

            // Assert: Verify the result
            assertThat(result).isEqualTo(insertedBook);
            assertThat(result).extracting(Book::getBookIsbn).isEqualTo("0123456789");
            assertThat(result).extracting(Book::getBookAuthor).isEqualTo("BookAuthor1001");
            assertThat(result).extracting(Book::getBookTitle).isEqualTo("BookTitle1001");
            verify(bookRepository, times(1)).insert(any(Book.class));
        }

        @Test
        @DisplayName("Duplicate book throws BookDuplicationError with the same Isbn number")
        void duplicateBookThrowsBookDuplicationErrorWithTheSameIsbnNumber() {
            CreateBook createBook = new CreateBook(
                    "BookTitle1001",
                    "BookAuthor1001",
                    "0123456789",
                    "BookDescription1001",
                    "1900-01-01",
                    "1"
            );

            // Create another instance to simulate the inserted book
            Book insertedBook = new Book();
            insertedBook.setBookID(6L);
            insertedBook.setBookTitle("BookTitle1001");
            insertedBook.setBookAuthor("BookAuthor1001");
            insertedBook.setBookIsbn("0123456789");
            insertedBook.setBookDescription("BookDescription1001");
            insertedBook.setBookPagesNumber(1L);
            insertedBook.setBookPublishDate(LocalDate.parse("1900-01-01"));

            // Mock the mapping behavior
            var mappedBook = BookMapper.mapToBook(createBook);
            mappedBook.setBookID(insertedBook.getBookID());


            // Mock duplicate ISBN check
            Mockito.when(bookService.getBookByISBN(eq("0123456789"))).thenReturn(Optional.of(mappedBook));

            // Act & Assert
            BookDuplicationError exception = assertThrows(BookDuplicationError.class, () ->
                    bookService.createBook(createBook)
            );

            assertThat(exception.getMessage()).isEqualTo(
                    "Book with: 0123456789 BookAuthor1001 BookTitle1001 already exists"
            );

            // Verify no insertion happens
            Mockito.verify(bookRepository, Mockito.never()).insert(any(Book.class));
        }


        @Test
        @DisplayName("DuplicationError thrown when title and author combination exists in database")
        void duplicationErrorThrownWhenTitleAndAuthorCombinationExistsInDatabase() {
            // Arrange: Create the input CreateBook object
            CreateBook createBook = new CreateBook(
                    "Books Title1",
                    "Book Author101",
                    "0123456789",
                    "BookDescription101",
                    "1900-01-01",
                    "1"
            );

            // Create and populate the mocked mappedBook instance
            Book mappedBook = new Book();
            mappedBook.setBookTitle("Books Title1");
            mappedBook.setBookAuthor("Book Author101");
            mappedBook.setBookIsbn("0123456789");
            mappedBook.setBookDescription("BookDescription101");
            mappedBook.setBookPagesNumber(1L);
            mappedBook.setBookPublishDate(LocalDate.parse("1900-01-01"));
            mappedBook.setBookID(6L);


            Book mockBook = new Book();
            mockBook.setBookTitle("Books Title1");
            mockBook.setBookAuthor("Book Author101");
            mockBook.setBookIsbn("0123456789");
            mockBook.setBookDescription("BookDescription101");
            mockBook.setBookPagesNumber(1L);
            mockBook.setBookPublishDate(LocalDate.parse("1900-01-01"));
            mockBook.setBookID(6L);

            Mockito.when(
                            bookRepository
                                    .getBookTitleAndBookAuthor(mappedBook.getBookTitle(), mappedBook.getBookAuthor()))
                    .thenReturn(Optional.of(mockBook)).thenThrow(new BookDuplicationError());

            assertThatThrownBy(() -> bookService.createBook(createBook)).isInstanceOf(BookDuplicationError.class)
                    .hasMessage("Book with: 0123456789 Book Author101 Books Title1 already exists");


            verify(bookRepository, Mockito.never()).insert(any(Book.class));


        }


        @Test
        @DisplayName("Updating Book and invalid Id will throw exception")
        void updatingBookAndInvalidIdWillThrowException() {

            UpdateBook updateBook = new UpdateBook();
            updateBook.setTitle("BookTitle101");
            updateBook.setAuthor("BookAuthor101");
            updateBook.setIsbn("0123456789");
            updateBook.setPages("9");
            updateBook.setDescription("Description101");

            when(bookRepository.findById(anyLong())).thenReturn(Optional.empty())
                    .thenThrow(BookNotFound.class);

            assertThatThrownBy(() ->  bookService.updateBook(updateBook, 1L)).isInstanceOf(BookNotFound.class)
                    .hasMessage("Book with id: 1 was not found in database");

            verify(bookRepository, Mockito.never()).insert(any(Book.class));

        }


        @Test
        @DisplayName("Updating book with valid id returns an Update Object")
        void updatingBookWithValidIdReturnsAnUpdateObject() {
            UpdateBook updateBook = new UpdateBook();
            updateBook.setTitle("BookTitle101");
            updateBook.setAuthor("BookAuthor101");
            updateBook.setIsbn("0123456789");
            updateBook.setPages("9");
            updateBook.setDescription("Description101");
            updateBook.setPublishedYear("1999-01-01");

            Book oldBook = new Book();
            oldBook.setBookTitle("BookTitle101");
            oldBook.setBookAuthor("BookAuthor101");
            oldBook.setBookIsbn("0123456789");
            oldBook.setBookDescription("BookDescription101");
            oldBook.setBookPagesNumber(1L);
            oldBook.setBookPublishDate(LocalDate.parse("1900-01-01"));
            oldBook.setBookID(6L);


            Book mockBook = new Book();
            mockBook.setBookTitle("BookTitle101");
            mockBook.setBookAuthor("BookAuthor101");
            mockBook.setBookIsbn("0123456789");
            mockBook.setBookDescription("BookDescription101");
            mockBook.setBookPagesNumber(9L);
            mockBook.setBookPublishDate(LocalDate.parse("1999-01-01"));
            mockBook.setBookID(6L);


            when(bookRepository.findById(eq(6L))).thenReturn(Optional.of(mockBook));

           var result = bookService.updateBook(updateBook, 6L);

            verify(bookRepository, Mockito.times(1)).update(any(Book.class));


            assertThat(result.getBookPagesNumber()).isNotEqualTo(oldBook.getBookPagesNumber());
            assertThat(result.getBookPublishDate()).isNotEqualTo(oldBook.getBookPublishDate());

        }

        @Test
        @DisplayName("patch with invalid or in existent id throws exception")
        void patchWithInvalidOrInExistentIdThrowsException() {

            PatchBook patchBook = new PatchBook();
            patchBook.setTitle("BookTitle101");
            patchBook.setAuthor("BookAuthor101");
            patchBook.setIsbn("0123456789");
            patchBook.setPages("9");
            patchBook.setDescription("Description101");

            when(bookRepository.findById(anyLong())).thenReturn(Optional.empty())
                    .thenThrow(BookNotFound.class);

            assertThatThrownBy(() ->  bookService.patchBook(patchBook, 1L)).isInstanceOf(BookNotFound.class)
                    .hasMessage("Book with id: 1 was not found in database");

            verify(bookRepository, Mockito.never()).insert(any(Book.class));

        }


        @Test
        @DisplayName("patch with valid Id update a single field")
        void patchWithValidIdUpdateASingleField() {

            PatchBook patchBook = new PatchBook();
            patchBook.setTitle("BookTitle201");


            Book oldBook = new Book();
            oldBook.setBookTitle("BookTitle101");
            oldBook.setBookAuthor("BookAuthor101");
            oldBook.setBookIsbn("0123456789");
            oldBook.setBookDescription("BookDescription101");
            oldBook.setBookPagesNumber(1L);
            oldBook.setBookPublishDate(LocalDate.parse("1900-01-01"));
            oldBook.setBookID(6L);


            Book mockBook = new Book();
            mockBook.setBookTitle("BookTitle201");
            mockBook.setBookAuthor("BookAuthor101");
            mockBook.setBookIsbn("0123456789");
            mockBook.setBookDescription("BookDescription101");
            mockBook.setBookPagesNumber(9L);
            mockBook.setBookPublishDate(LocalDate.parse("1999-01-01"));
            mockBook.setBookID(6L);


            when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBook));

            var result = bookService.patchBook(patchBook, 6L);

            verify(bookRepository, Mockito.times(1)).update(any(Book.class));

            assertThat(result.getBookTitle()).isNotEqualTo(oldBook.getBookTitle());


        }

        @Test
        @DisplayName("Deletion fails when Invalid id is provided throws exceptions ")
        void deletionFailsWhenInvalidIdIsProvidedThrowsExceptions() {

            when(bookRepository.findById(anyLong())).thenReturn(Optional.empty())
                    .thenThrow(BookNotFound.class);

            assertThatThrownBy(() ->  bookService.deleteBook(1L)).isInstanceOf(BookNotFound.class)
                    .hasMessage("Book with id: 1 was not found in database");

            verify(bookRepository, Mockito.never()).delete(any(Book.class));

        }


        @Test
        @DisplayName("Deletion preformed with id")
        void deletionPreformedWithId() {

            List<Book> mockBooks = List.of(books().get(0),  books().get(1));
            when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mockBooks.get(0)));

            Book book = mockBooks.get(0);
            bookService.deleteBook(1L);
            verify(bookRepository, Mockito.times(1)).delete(book);


        }


    }



}
