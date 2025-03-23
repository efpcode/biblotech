package biblotech.business;



import biblotech.dto.BookAuthorsQueryResponse;
import biblotech.dto.BookFilterQueryResponse;
import biblotech.dto.BookResponse;
import biblotech.dto.SortedBookPageResponse;
import biblotech.entity.Book;
import biblotech.exceptions.BookNotFound;
import biblotech.exceptions.InvalidBookPage;
import biblotech.exceptions.InvalidSearchQuery;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

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
    private SortedBookPageResponse sortedBookPageResponse;

    @Mock
    private Order<Book> bookOrder;




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
            Order<Book> bookOrder =  Order.by( Sort.asc("bookTitle")); // Example sort order
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
                    eq("ks"),Mockito.any(PageRequest.class),
                    Mockito.any(Order.class)))
                    .thenReturn(mockBookPage);

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder =  Order.by( Sort.asc("bookAuthor"));

            Page<Book> result = bookService.getBooksPages("ks",null, pageRequest, bookOrder);
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
            Order<Book> bookOrder =  Order.by( Sort.asc("bookTitle"));
            Page<Book> result = bookService.getBooksPages(null,"oks", pageRequest, bookOrder);

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
            Order<Book> bookOrder =  Order.by(Sort.asc("bookTitle"));


         assertThrows(InvalidSearchQuery.class, () -> bookService.getBooksPages("","",pageRequest, bookOrder));
        }

        @Test
        @DisplayName("testGetBookPages_Invalid Or Corrupt Page")
        void testGetBookPagesInvalidOrCorruptPage() {

            assertThrows(InvalidBookPage.class, () -> {
                bookService.getBooksPages(null, null, null, null);
            }).getMessage().matches("Page corrupted found!: null");
        }

    }

    @Nested
    class FindBookByFilterQueriesTestSuite{

        @Test
        @DisplayName("getBooksQueryFilter_TwoParameterMust be passed as minimum or exception")
        void getBooksQueryFilterTwoParameterMustBePassedAsMinimumOrException() {

            PageRequest pageRequest = PageRequest.ofPage(1L, 2, true);
            Order<Book> bookOrder =  Order.by( Sort.asc("bookTitle"));

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
        @DisplayName("getBooksQueryFilter_PassingOnlyAuthorAndTitle")
        void getBooksQueryFilterPassingOnlyAuthorAndTitle() {

            List<Book> mockBooks =  List.of(books().get(1));
            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(1);

            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder =  Order.by( Sort.asc("bookTitle"));


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

            List<Book> mockBooks =   List.of(books().get(1));
            Page<Book> mockBookPage = Mockito.mock(Page.class);
            Mockito.when(mockBookPage.content()).thenReturn(mockBooks);
            Mockito.when(mockBookPage.totalPages()).thenReturn(1L);
            Mockito.when(mockBookPage.numberOfElements()).thenReturn(1);
            PageRequest pageRequest = PageRequest.ofPage(1L, 1, true);
            Order<Book> bookOrder =  Order.by( Sort.asc("bookTitle"));

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
        
        
        
    }





}
