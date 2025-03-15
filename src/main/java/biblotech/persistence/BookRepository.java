package biblotech.persistence;

import biblotech.entity.Book;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
    // Query like search for author
    @Query("SELECT b FROM Book b WHERE b.bookAuthor LIKE :bookAuthor")
    Page<Book> findByBookAuthorLike(String bookAuthor, PageRequest pageRequest, Order<Book> bookOrder);


    @Find
    Optional<Book> findByBookTitleAndBookAuthor(String bookTitle, String bookAuthor);

    @Find
    Optional<Book> findByBookISBN(String bookIsbn);


}
