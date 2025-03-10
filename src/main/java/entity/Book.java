package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "library")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookID;

    @Column(name="bookTitle")
    private String bookTitle;

    @Column(name="bookAuthour")
    private String bookAuthor;

    @Column(name="bookISBN")
    private String bookIsbn;

    @Column(name="bookDescription")
    private String bookDescription;

    @Column(name="bookPageNumber")
    private Long bookPagesNumber;

    @Column(name="bookPublishDate")
    private Date bookPublishDate;

    @ColumnDefault("(((bookauthour)) || (booktitle))")
    @Column(name = "bookauthortitle", length = Integer.MAX_VALUE)
    private String bookauthortitle;

    public String getBookauthortitle() {
        return bookauthortitle;
    }

    public void setBookauthortitle(String bookauthortitle) {
        this.bookauthortitle = bookauthortitle;
    }

    public Long getBookID() {
        return bookID;
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
                ", bookauthortitle='" + bookauthortitle + '\'' +
                '}';
    }
}
