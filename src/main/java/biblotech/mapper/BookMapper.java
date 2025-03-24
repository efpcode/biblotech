package biblotech.mapper;

import biblotech.dto.*;
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


    public static Book fromUpdateOrPatchBook(Book book, BookBaseFields bookUpdate) {
        if (book == null || bookUpdate == null) {
            return null;
        }

        if (bookUpdate.getTitle() != null && !bookUpdate.getTitle().trim().isEmpty()) {
            book.setBookTitle(bookUpdate.getTitle());
        }
        if (bookUpdate.getAuthor() != null && !bookUpdate.getAuthor().trim().isEmpty()) {
            book.setBookAuthor(bookUpdate.getAuthor());
        }
        if (bookUpdate.getIsbn()!= null && !bookUpdate.getIsbn().trim().isEmpty()) {
            book.setBookIsbn(ISBNMapper.mapToBook(bookUpdate.getIsbn()));
        }
        if (bookUpdate.getDescription() != null && !bookUpdate.getDescription().trim().isEmpty()) {
            book.setBookDescription(bookUpdate.getDescription());
        }
        if (bookUpdate.getPublishedYear() != null && !bookUpdate.getPublishedYear().trim().isEmpty()) {
            book.setBookPublishDate(YearMapper.getYearFromString(bookUpdate.getPublishedYear()));
        }
        if (bookUpdate.getPages() != null && !bookUpdate.getPages().trim().isEmpty()) {
            book.setBookPagesNumber(PagesMapper.mapToLong(bookUpdate.getPages()));
        }

        return book;

    }


    public static BookBaseFields mapToUpdateOrPatchBook(Book book, Object dataType){
        BookBaseFields newBook;

        if (book == null || dataType == null) {
            return null;
        }

        if (dataType instanceof UpdateBook) {
            newBook = new UpdateBook();

        }else {
            newBook = new PatchBook();
        }

        newBook.setTitle(book.getBookTitle());
        newBook.setAuthor(book.getBookAuthor());
        newBook.setIsbn(book.getBookIsbn());
        newBook.setDescription(book.getBookDescription());
        newBook.setPublishedYear(String.valueOf(book.getBookPublishDate()));
        newBook.setPages(String.valueOf(book.getBookPagesNumber()));
        return newBook;
    }


}
