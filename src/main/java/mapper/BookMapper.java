package mapper;

import dto.BookResponse;
import dto.CreateBook;
import entity.Book;

public class BookMapper {
    private BookMapper() {}

    public static BookResponse maptoBookResponse(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponse(
                book.getBookID(),
                book.getBookTitle(),
                book.getBookAuthor(),
                book.getBookIsbn(),
                book.getBookDescription(),
                book.getBookPublishDate(),
                book.getBookPagesNumber()
        );
    }

    public static Book maptoBook(CreateBook book) {
        if (book == null) {
            return null;
        }
        Book newBook = new Book();
        newBook.setBookTitle(book.title());
        newBook.setBookAuthor(book.author());
        newBook.setBookIsbn(book.isbn());
        newBook.setBookDescription(book.description());
        newBook.setBookPublishDate(book.bookPublishDate());
        newBook.setBookPagesNumber(book.bookPagesNumber());
        return newBook;

    }
}
