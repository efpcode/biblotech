package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID", unique = true, nullable = false)
    private Long bookID;

    @Column(name="bookTitle", nullable = false)
    private String bookTitle;

    @Column(name="bookAuthor", nullable = false)
    private String bookAuthor;

    @Column(name="bookISBN",  nullable = false)
    private String bookIsbn;

    @Column(name="bookDescription", nullable = false)
    private String bookDescription;

    @Column(name="bookPageNumber", nullable = false)
    private Long bookPagesNumber;

    @Past
    @Column(name="bookPublishDate",  nullable = false)
    private Date bookPublishDate;



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

    public Date getBookPublishDate() {
        return bookPublishDate;
    }

    public void setBookPublishDate(Date bookPublishDate) {
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
