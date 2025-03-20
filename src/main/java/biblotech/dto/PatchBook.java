package biblotech.dto;

import biblotech.rules.ValidPatchBook;

import java.util.Objects;

@ValidPatchBook
public class PatchBook extends BookBaseFields {
    private String author;
    private String title;
    private String isbn;
    private String description;
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
}
