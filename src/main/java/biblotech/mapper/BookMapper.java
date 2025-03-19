package biblotech.mapper;

import biblotech.dto.BookResponse;
import biblotech.dto.UpdateBook;
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

    public static Book fromUpdateBook(Book book, UpdateBook bookUpdate) {
        if (book == null || bookUpdate == null) {
            return null;
        }

        if (bookUpdate.title() != null && !bookUpdate.title().trim().isEmpty()) {
            book.setBookTitle(bookUpdate.title());
        }
        if (bookUpdate.author() != null && !bookUpdate.author().trim().isEmpty()) {
            book.setBookAuthor(bookUpdate.author());
        }
        if (bookUpdate.isbn() != null && !bookUpdate.isbn().trim().isEmpty()) {
            book.setBookIsbn(bookUpdate.isbn());
        }
        if (bookUpdate.description() != null && !bookUpdate.description().trim().isEmpty()) {
            book.setBookDescription(bookUpdate.description());
        }
        if (bookUpdate.publishedYear() != null && !bookUpdate.publishedYear().trim().isEmpty()) {
            book.setBookPublishDate(YearMapper.getYearFromString(bookUpdate.publishedYear()));
        }
        if (bookUpdate.pages() != null && !bookUpdate.pages().trim().isEmpty()) {
            book.setBookPagesNumber(PagesMapper.mapToLong(bookUpdate.pages()));
        }

        return book;

    }




}
