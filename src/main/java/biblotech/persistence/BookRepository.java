package biblotech.persistence;

import biblotech.entity.Book;
import biblotech.rules.ValidBookAuthor;
import biblotech.rules.ValidBookTitle;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Find
    Optional<Book> findByTitleAndAuthor(String bookTitle, String bookAuthor);

    @Find
    Optional<Book> findByISBN(String bookIsbn);
}
