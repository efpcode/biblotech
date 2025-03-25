package biblotech.dto;

import biblotech.rules.ValidPatchBook;
import biblotech.rules.util.DateDeserializer;
import jakarta.json.bind.annotation.JsonbTypeDeserializer;

import java.util.Objects;

@ValidPatchBook
public class PatchBook extends BookBaseFields {
    private String author;
    private String title;
    private String isbn;
    private String description;
    @JsonbTypeDeserializer(DateDeserializer.class)
    private String publishedYear;
    private String pages;

    public PatchBook(
            String title,
            String author,
            String isbn,
            String description,
            String publishedYear,
            String pages) {
        super(title, author, isbn, description, publishedYear, pages);


    }

    public PatchBook(){
        super();
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
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
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
        PatchBook patchBook = (PatchBook) o;
        return Objects.equals(author, patchBook.author) && Objects.equals(title, patchBook.title) && Objects.equals(isbn, patchBook.isbn) && Objects.equals(description, patchBook.description) && Objects.equals(publishedYear, patchBook.publishedYear) && Objects.equals(pages, patchBook.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, title, isbn, description, publishedYear, pages);
    }

    @Override
    public String toString() {
        return "PatchBook{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", publishedYear='" + publishedYear + '\'' +
                ", pages='" + pages + '\'' +
                '}';
    }

    @Override
    public boolean isAllFieldsEmpty() {
        return title == null && author == null && isbn == null  && description==null && publishedYear ==null && pages ==null;

    }


}
