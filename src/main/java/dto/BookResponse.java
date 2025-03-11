package dto;
import entity.Book;

import java.util.Date;

public record BookResponse (Long id, String title, String author, String isbn, String description, Date publishedYear, Long pages
) {

    public BookResponse(Book book){
        this(book.getBookID(),book.getBookTitle(), book.getBookAuthor(),book.getBookIsbn(), book.getBookDescription(), book.getBookPublishDate(), book.getBookPagesNumber());
    }


    public static BookResponse fromEntity(Book book){
        return new BookResponse(book.getBookID(), book.getBookTitle(), book.getBookAuthor(), book.getBookIsbn(), book.getBookDescription(), book.getBookPublishDate(), book.getBookPagesNumber());
    }




}
