package biblotech.dto;

import biblotech.rules.*;
import biblotech.rules.util.DateDeserializer;
import jakarta.json.bind.annotation.JsonbTypeDeserializer;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class UpdateBook extends BookBaseFields {

    @ValidBookTitle private String title;
    @ValidBookAuthor private String author;
    @ValidBookISBN private String isbn;
    @ValidBookDescription private String description;
    @ValidBookPublishedYear @JsonbTypeDeserializer(DateDeserializer.class) @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") private String publishedYear;
    @ValidateBookPages private String pages;


    public UpdateBook(
            String title,
            String author,
            String isbn,
            String description,
            String publishedYear,
            String pages) {
        super(title, author, isbn, description, publishedYear, pages);
    }

    public UpdateBook(){
        super();
    }


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getPublishedYear() {
        return publishedYear;
    }

    @Override
    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    @Override
    public String getPages() {
        return pages;
    }

    @Override
    public void setPages(String pages) {
        this.pages = pages;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UpdateBook that = (UpdateBook) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(isbn, that.isbn) && Objects.equals(description, that.description) && Objects.equals(publishedYear, that.publishedYear) && Objects.equals(pages, that.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, author, isbn, description, publishedYear, pages);
    }

    @Override
    public String toString() {
        return "UpdateBook{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", publishedYear='" + publishedYear + '\'' +
                ", pages='" + pages + '\'' +
                '}';
    }
}
