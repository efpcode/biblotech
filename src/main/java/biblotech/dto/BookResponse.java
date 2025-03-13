package biblotech.dto;
import biblotech.entity.Book;
import java.time.LocalDate;

public record BookResponse (Long id, String title, String author, String isbn, String description, LocalDate publishedYear, Long pages
) {

    public BookResponse(Book book){
        this(book.getBookID(), book.getBookTitle(), book.getBookAuthor(),book.getBookIsbn(), book.getBookDescription(), book.getBookPublishDate(), book.getBookPagesNumber());
    }


    public static BookResponse fromEntity(Book book){
        return new BookResponse(book.getBookID(), book.getBookTitle(), book.getBookAuthor(), book.getBookIsbn(), book.getBookDescription(), book.getBookPublishDate(), book.getBookPagesNumber());
    }




}
