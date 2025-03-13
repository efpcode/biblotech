package biblotech.persistence;

import biblotech.entity.Book;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
