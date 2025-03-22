package biblotech.business;



import biblotech.dto.BookResponse;
import biblotech.entity.Book;
import biblotech.exceptions.BookNotFound;
import biblotech.persistence.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private Book book;

    @Mock private
    BookRepository bookRepository;

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

        book1.setBookAuthor("Book Author");
        book2.setBookAuthor("Book Author");
        book3.setBookAuthor("Book Author");
        book4.setBookAuthor("Book Author");
        book5.setBookAuthor("Book Author");
        book6.setBookAuthor("Book Author");

        book1.setBookTitle("Book Title1");
        book2.setBookTitle("Book Title10");
        book3.setBookTitle("Book Title100");
        book4.setBookTitle("Book Title500");
        book5.setBookTitle("Book Title101");
        book6.setBookTitle("Book Title103");

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

        book1.setBookPublishDate(LocalDate.of(1956, 1, 1));
        book2.setBookPublishDate(LocalDate.of(1956, 1, 1));
        book3.setBookPublishDate(LocalDate.of(1956, 1, 1));
        book4.setBookPublishDate(LocalDate.of(1956, 1, 1));
        book5.setBookPublishDate(LocalDate.of(1956, 1, 1));
        book6.setBookPublishDate(LocalDate.of(1956, 1, 1));

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
        book.setBookPagesNumber(101L);    }

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


    @Test
    @DisplayName("Calling getBookAuthorAndTitle returns a book ")
    void callingGetBookAuthorAndTitleReturnsABook() {

        when(bookRepository.getBookTitleAndBookAuthor("Book Title10", "Book Author")).thenReturn(Optional.of(books().get(1)));
        var bookNew = bookService.getBookAuthorAndTitle(books().get(1));

        assertThat(bookNew).contains(books().get(1));
    }


    
    
    






}
