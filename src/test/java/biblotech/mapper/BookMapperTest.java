package biblotech.mapper;

import biblotech.dto.BookResponse;
import biblotech.dto.CreateBook;
import biblotech.dto.PatchBook;
import biblotech.dto.UpdateBook;
import biblotech.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;


class BookMapperTest {
    private Book book = new Book();

    @BeforeEach
    void setUp() {
        book.setBookID(1L);
        book.setBookTitle("Book Title");
        book.setBookAuthor("Book Author");
        book.setBookIsbn("0123456789");
        book.setBookDescription("Book Description");
        book.setBookPublishDate(LocalDate.parse("2021-04-20"));
        book.setBookPagesNumber(10L);

    }


    @Test
    @DisplayName("Conversion from CreateBook to Book")
    void conversionFromCreateBookToBook() {
        CreateBook createBook = new CreateBook(
                "Book Title",
                "Book Author",
                "0123456789",
                "Book Description",
                "2021-04-20",
                "10"
                );

        var result = BookMapper.mapToBook(createBook);

        assertThat(result).isNotNull();
        assertThat(result.getBookID()).isNull();
        assertThat(result.getBookID()).isNotEqualTo(book.getBookID());
        assertThat(result.getBookTitle()).isEqualTo(book.getBookTitle());
        assertThat(result.getBookAuthor()).isEqualTo(book.getBookAuthor());
        assertThat(result.getBookIsbn()).isEqualTo(book.getBookIsbn());
        assertThat(result.getBookDescription()).isEqualTo(book.getBookDescription());
        assertThat(result.getBookPublishDate()).isEqualTo(book.getBookPublishDate());
        assertThat(result.getBookPagesNumber()).isEqualTo(book.getBookPagesNumber());



    }

    @Test
    @DisplayName("Conversion from CreateBook to Book with Null returns Null")
    void conversionFromCreateBookToBookWithNullReturnsNull() {

        CreateBook createBook = null;
        var result = BookMapper.mapToBook(createBook);
        assertThat(result).isNull();

    }



    @Test
    @DisplayName("Book Conversion to BookResponse contains the same values")
    void bookConversionToBookResponseContainsTheSameValues() {
        Book toBeMapped = new Book();
        toBeMapped.setBookID(1L);
        toBeMapped.setBookTitle("Book Title");
        toBeMapped.setBookAuthor("Book Author");
        toBeMapped.setBookIsbn("0123456789");
        toBeMapped.setBookDescription("Book Description");
        toBeMapped.setBookPublishDate(LocalDate.parse("2021-04-20"));
        toBeMapped.setBookPagesNumber(10L);
        BookResponse bookResponse = BookMapper.mapToBookResponse(toBeMapped);

        assertThat(bookResponse).isNotNull();
        assertThat(bookResponse.id()).isEqualTo(book.getBookID());
        assertThat(bookResponse.title()).isEqualTo(book.getBookTitle());
        assertThat(bookResponse.isbn()).isEqualTo(book.getBookIsbn());
        assertThat(bookResponse.description()).isEqualTo(book.getBookDescription());
        assertThat(bookResponse.publishedYear()).isEqualTo(book.getBookPublishDate());
        assertThat(bookResponse.pages()).isEqualTo(book.getBookPagesNumber());

    }

    @Test
    @DisplayName("Book conversion with null return null")
    void bookConversionWithNullReturnNull() {
        Book nullBook = null;
        BookResponse bookResponse = BookMapper.mapToBookResponse(nullBook);
        assertThat(bookResponse).isNull();

    }


    @Test
    @DisplayName("From Update or Patch object to new book null values do not change old object ")
    void fromUpdateOrPatchObjectToNewBookNullValuesDoNotChangeOldObject() {
        UpdateBook updateBook = new UpdateBook();

        updateBook.setPublishedYear(null);
        updateBook.setDescription(null);
        updateBook.setAuthor(null);
        updateBook.setIsbn(null);
        updateBook.setTitle(null);
        updateBook.setPages(null);

        Book book2 = new Book();

        book2.setBookID(1L);
        book2.setBookTitle("Book Title");
        book2.setBookAuthor("Book Author");
        book2.setBookIsbn("0123456789");
        book2.setBookDescription("Book Description");
        book2.setBookPublishDate(LocalDate.parse("2021-04-20"));
        book2.setBookPagesNumber(10L);


        var result = BookMapper.fromUpdateOrPatchBook(book, updateBook);

        assertThat(result).isNotNull();
        assertThat(result.getBookID()).isEqualTo(book2.getBookID());
        assertThat(result.getBookTitle()).isEqualTo(book2.getBookTitle());
        assertThat(result.getBookAuthor()).isEqualTo(book2.getBookAuthor());
        assertThat(result.getBookIsbn()).isEqualTo(book2.getBookIsbn());
        assertThat(result.getBookDescription()).isEqualTo(book2.getBookDescription());
        assertThat(result.getBookPublishDate()).isEqualTo(book2.getBookPublishDate());
        assertThat(result.getBookPagesNumber()).isEqualTo(book2.getBookPagesNumber());



    }

    @Test
    @DisplayName("fromUpdate Or Patch object to new book empty values do not change old object")
    void fromUpdateOrPatchObjectToNewBookEmptyValuesDoNotChangeOldObject() {

        UpdateBook updateBook = new UpdateBook();

        updateBook.setPublishedYear("");
        updateBook.setDescription("");
        updateBook.setAuthor("");
        updateBook.setIsbn("");
        updateBook.setTitle("");
        updateBook.setPages("");

        Book book2 = new Book();

        book2.setBookID(1L);
        book2.setBookTitle("Book Title");
        book2.setBookAuthor("Book Author");
        book2.setBookIsbn("0123456789");
        book2.setBookDescription("Book Description");
        book2.setBookPublishDate(LocalDate.parse("2021-04-20"));
        book2.setBookPagesNumber(10L);


        var result = BookMapper.fromUpdateOrPatchBook(book, updateBook);

        assertThat(result).isNotNull();
        assertThat(result.getBookID()).isEqualTo(book2.getBookID());
        assertThat(result.getBookTitle()).isEqualTo(book2.getBookTitle());
        assertThat(result.getBookAuthor()).isEqualTo(book2.getBookAuthor());
        assertThat(result.getBookIsbn()).isEqualTo(book2.getBookIsbn());
        assertThat(result.getBookDescription()).isEqualTo(book2.getBookDescription());
        assertThat(result.getBookPublishDate()).isEqualTo(book2.getBookPublishDate());
        assertThat(result.getBookPagesNumber()).isEqualTo(book2.getBookPagesNumber());




    }

    @Test
    @DisplayName("fromUpdateOrPatchObject to Book if valid value book is updates")
    void fromUpdateOrPatchObjectToBookIfValidValueBookIsUpdates() {

        UpdateBook updateBook = new UpdateBook();
        updateBook.setPublishedYear("2025-03-24");
        updateBook.setDescription("Description");
        updateBook.setAuthor("Author");
        updateBook.setIsbn("4123456789");
        updateBook.setTitle("Title");
        updateBook.setPages("100");

        Book book2 = new Book();

        book2.setBookID(1L);
        book2.setBookTitle("Book Title");
        book2.setBookAuthor("Book Author");
        book2.setBookIsbn("0123456789");
        book2.setBookDescription("Book Description");
        book2.setBookPublishDate(LocalDate.parse("2021-04-20"));
        book2.setBookPagesNumber(10L);

        var result = BookMapper.fromUpdateOrPatchBook(book, updateBook);
        assertThat(result).isNotNull();
        assertThat(result.getBookID()).isEqualTo(book2.getBookID());
        assertThat(result.getBookTitle()).isNotEqualTo(book2.getBookTitle());
        assertThat(result.getBookAuthor()).isNotEqualTo(book2.getBookAuthor());
        assertThat(result.getBookIsbn()).isNotEqualTo(book2.getBookIsbn());
        assertThat(result.getBookDescription()).isNotEqualTo(book2.getBookDescription());
        assertThat(result.getBookPublishDate()).isNotEqualTo(book2.getBookPublishDate());
        assertThat(result.getBookPagesNumber()).isNotEqualTo(book2.getBookPagesNumber());


    }

    @Test
    @DisplayName("fromUpdateorPatch object if passed null returns null")
    void fromUpdateorPatchObjectIfNullReturnsNull() {
        UpdateBook updateBook = new UpdateBook();
        Book book2 = null;

        UpdateBook updateBook2 = null;
        Book book3 = new Book();

        var result = BookMapper.fromUpdateOrPatchBook(book2, updateBook);
        assertThat(result).isNull();


        var result2 = BookMapper.fromUpdateOrPatchBook(book3, updateBook2);
        assertThat(result2).isNull();


    }


    @Test
    @DisplayName("mapToUpdateOrPatch from book if passed null returns null")
    void mapToUpdateOrPatchFromBookIfPassedNullReturnsNull() {
        UpdateBook updateBook = new UpdateBook();
        Book book2 = null;

        UpdateBook updateBook2 = null;
        Book book3 = new Book();

        var result = BookMapper.mapToUpdateOrPatchBook(book2, updateBook);
        assertThat(result).isNull();

        var result2 = BookMapper.mapToUpdateOrPatchBook(book3, updateBook2);
        assertThat(result2).isNull();


    }


    @Test
    @DisplayName("mapToUpdateOrPatch object if Update passed Update returned ")
    void mapToUpdateOrPatchObjectIfUpdatePassedUpdateReturned() {

        UpdateBook updateBook = new UpdateBook();

       var result = BookMapper.mapToUpdateOrPatchBook(book, updateBook);
       assertThat(result).isNotNull();
       assertThat(result).isExactlyInstanceOf(UpdateBook.class);

    }

    @Test
    @DisplayName("mapToUpdateOrPatch object if Patch passed Patch returned")
    void mapToUpdateOrPatchObjectIfPatchPassedPatchReturned() {
        PatchBook patchBook = new PatchBook();
        var result = BookMapper.mapToUpdateOrPatchBook(book, patchBook);
        assertThat(result).isNotNull();
        assertThat(result).isExactlyInstanceOf(PatchBook.class);

    }


}
