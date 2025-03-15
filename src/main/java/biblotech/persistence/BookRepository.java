package biblotech.persistence;

import biblotech.entity.Book;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
    // Query like search for author and sorted by title in descending order
    @Query("SELECT b FROM Book b WHERE b.bookAuthor LIKE :bookAuthor ORDER BY b.bookTitle DESC")
    Page<Book> findByBookAuthorLikeOrderByBookTitleDesc(String bookAuthor, PageRequest pageRequest);

    // Query like search for author and sorted by title in acending order
    @Query("SELECT b FROM Book b WHERE b.bookAuthor LIKE :bookAuthor ORDER BY b.bookTitle ASC")
    Page<Book> findByBookAuthorLikeOrderByBookTitleAsc(String bookAuthor, PageRequest pageRequest);

    @Find
    Optional<Book> findByBookTitleAndBookAuthor(String bookTitle, String bookAuthor);

    @Find
    Optional<Book> findByBookISBN(String bookIsbn);


}
