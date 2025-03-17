package biblotech.persistence;

import biblotech.entity.Book;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;


import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    // Query like search for author with validation for author name Jane is query whereas is not jane
    @Query("SELECT b FROM Book b WHERE LOWER(b.bookAuthor) ILIKE (CONCAT('%', :bookAuthor, '%'))")
    Page<Book> findByBookAuthorLike(String bookAuthor, PageRequest pageRequest, Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE LOWER(b.bookTitle) ILIKE (CONCAT('%', :bookTitle, '%'))")
    Page<Book> findBookTitleLike(String bookTitle, PageRequest pageRequest, Order<Book> bookOrder);

    @Find
    Page<Book> findAll(PageRequest pageRequest, Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE b.bookTitle ILIKE CONCAT('%', :bookTitle, '%') AND b.bookAuthor ILIKE CONCAT('%', :bookAuthor, '%')")
    List<Book> findByBookTitleAndBookAuthorIgnoreCase(String bookTitle, String bookAuthor);

    @Find
    Optional<Book> findByBookIsbn(String bookIsbn);


}
