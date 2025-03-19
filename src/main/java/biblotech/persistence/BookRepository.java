package biblotech.persistence;

import biblotech.entity.Book;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;


import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.bookAuthor) ILIKE (CONCAT('%', :bookAuthor, '%'))")
    Page<Book> findBookAuthorLike(String bookAuthor, PageRequest pageRequest, Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE LOWER(b.bookTitle) ILIKE (CONCAT('%', :bookTitle, '%'))")
    Page<Book> findBookTitleLike(String bookTitle, PageRequest pageRequest, Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE b.bookTitle ILIKE CONCAT('%', :bookTitle, '%') AND b.bookAuthor ILIKE CONCAT('%', :bookAuthor, '%')")
    Page<Book> findBookTitleAndBookAuthorIgnoreCasePage(String bookTitle, String bookAuthor, PageRequest pageRequest, Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE b.bookPublishDate >= :startDate AND b.bookPublishDate <= :endDate")
    Page<Book> findBookPublishDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, PageRequest pageRequest,  Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE b.bookPublishDate >= :startDate AND b.bookPublishDate <= :endDate AND b.bookTitle ILIKE CONCAT('%', :bookTitle, '%')  ")
    Page<Book> findBookPublishDateBetweenAndBookTitle(String bookTitle,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, PageRequest pageRequest,  Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE b.bookPublishDate >= :startDate AND b.bookPublishDate <= :endDate AND b.bookAuthor ILIKE CONCAT('%', :bookAuthor, '%')  ")
    Page<Book> findBookPublishDateBetweenAndBookAuthor(String bookAuthor,@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, PageRequest pageRequest,  Order<Book> bookOrder);

    @Query("SELECT b FROM Book b WHERE b.bookPublishDate >= :startDate AND b.bookPublishDate <= :endDate AND b.bookAuthor ILIKE CONCAT('%', :bookAuthor, '%') AND b.bookTitle ILIKE CONCAT('%', :bookTitle, '%') ")
    Page<Book> findBookPublishDateBetweenAndBookAuthorAndBookTitle(String bookAuthor, String bookTitle, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, PageRequest pageRequest,  Order<Book> bookOrder);

    @Find
    Page<Book> findAll(PageRequest pageRequest, Order<Book> bookOrder);


    @Find
    Optional<Book> findByBookIsbn(String bookIsbn);


    @Query("SELECT b FROM Book b WHERE b.bookTitle ILIKE CONCAT('%', :bookTitle, '%') AND b.bookAuthor ILIKE CONCAT('%', :bookAuthor, '%')")
    Optional<Book> getBookTitleAndBookAuthor(String bookTitle, String bookAuthor);


}
