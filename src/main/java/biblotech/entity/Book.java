package biblotech.entity;

import biblotech.rules.ValidBookAuthor;
import biblotech.rules.ValidBookISBN;
import biblotech.rules.ValidBookPublishDate;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import biblotech.rules.ValidBookTitle;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "book",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bookTitle", "bookAuthor"})
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID", unique = true, nullable = false)
    private Long bookID;

    @Column(name="bookTitle", nullable = false)
    @ValidBookTitle
    @Valid
    private String bookTitle;

    @Column(name="bookAuthor", nullable = false)
    @ValidBookAuthor
    @Valid
    private String bookAuthor;

    @Column(name="bookISBN",  nullable = false,  unique = true)
    @ValidBookISBN
    @Valid
    private String bookIsbn;

    @Size(min = 1, max = 1000)
    @NotBlank(message = "Description cannot be empty or null")
    @Column(name="bookDescription", nullable = false)
    private String bookDescription;

    @Positive(message = "Number of pages must be greater than zero")
    @Column(name="bookPageNumber", nullable = false)
    private Long bookPagesNumber;

    @JsonbDateFormat(value = "yyyy-MM-dd") // Specify the expected date format
    @PastOrPresent(message = "Publishing date for book must be in the past")
    @Column(name="bookPublishDate",  nullable = false)
    @ValidBookPublishDate
    @Valid
    private LocalDate bookPublishDate;



    public Long getBookID() {
        return bookID;
    }
    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Long getBookPagesNumber() {
        return bookPagesNumber;
    }

    public void setBookPagesNumber(Long bookPagesNumber) {
        this.bookPagesNumber = bookPagesNumber;
    }

    public LocalDate getBookPublishDate() {
        return bookPublishDate;
    }

    public void setBookPublishDate(LocalDate bookPublishDate) {
        this.bookPublishDate = bookPublishDate;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Book book = (Book) o;
        return getBookID() != null && Objects.equals(getBookID(), book.getBookID());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                ", bookPagesNumber=" + bookPagesNumber +
                ", bookPublishDate=" + bookPublishDate +
                '}';
    }
}
