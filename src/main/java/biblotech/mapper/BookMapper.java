package biblotech.mapper;

import biblotech.dto.BookResponse;
import biblotech.dto.CreateBook;
import biblotech.entity.Book;

public class BookMapper {
    private BookMapper() {}

    public static BookResponse mapToBookResponse(Book book) {
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

    public static Book mapToBook(CreateBook book) {
        if (book == null) {
            return null;
        }
        Book newBook = new Book();
        newBook.setBookTitle(book.title());
        newBook.setBookAuthor(book.author());
        newBook.setBookIsbn(ISBNMapper.mapToBook(book.isbn()));
        newBook.setBookDescription(book.description());
        newBook.setBookPublishDate(YearMapper.getYearFromString(book.publishedYear()));
        newBook.setBookPagesNumber(PagesMapper.mapToLong(book.pages()));
        return newBook;

    }
}
