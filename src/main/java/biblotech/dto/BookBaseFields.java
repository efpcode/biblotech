package biblotech.dto;


import java.util.Objects;

public abstract class BookBaseFields {
   private String title;
   private String author;
   private String isbn;
   private String description;
   private String publishedYear;
   private String pages;

    protected  BookBaseFields(String title, String author, String isbn, String description, String publishedYear, String pages) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.publishedYear = publishedYear;
        this.pages = pages;
    }

    public BookBaseFields() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public boolean isAllFieldsEmpty(){
        return this.getTitle().isEmpty() && this.getAuthor().isEmpty() && this.getIsbn().isEmpty()  && this.getDescription().isEmpty() && this.getPublishedYear().isEmpty() && this.getPages().isEmpty();
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookBaseFields that = (BookBaseFields) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(isbn, that.isbn) && Objects.equals(description, that.description) && Objects.equals(publishedYear, that.publishedYear) && Objects.equals(pages, that.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn, description, publishedYear, pages);
    }

    @Override
    public String toString() {
        return "BookBaseFields{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                ", publishedYear='" + publishedYear + '\'' +
                ", pages='" + pages + '\'' +
                '}';
    }
}
