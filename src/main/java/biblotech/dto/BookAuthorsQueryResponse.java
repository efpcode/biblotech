package biblotech.dto;

import biblotech.rules.ValidSortedBookOrder;
import biblotech.rules.ValidSortedBookQuery;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

import java.util.Objects;

public  class BookAuthorsQueryResponse {
    @QueryParam("author")
    private  String author;
    @QueryParam("title")
    private  String title;
    @QueryParam("pageNumber")
    private  @Positive Long pageNumber;
    @ValidSortedBookQuery
    @QueryParam("sortBy")
    @DefaultValue("title")
    private  String sortBy;
    @ValidSortedBookOrder
    @QueryParam("sortOrder")
    @DefaultValue("asc")
    private  String sortOrder;
    @QueryParam("pageSize")
    private  @Positive Integer pageSize;

    public BookAuthorsQueryResponse(){

    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Long getPageNumber() {
        return pageNumber;
    }


    public String getSortBy() {
        return sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BookAuthorsQueryResponse) obj;
        return Objects.equals(this.author, that.author) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.pageNumber, that.pageNumber) &&
                Objects.equals(this.sortBy, that.sortBy) &&
                Objects.equals(this.sortOrder, that.sortOrder) &&
                Objects.equals(this.pageSize, that.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, pageNumber, sortBy, sortOrder, pageSize);
    }

    @Override
    public String toString() {
        return "BookAuthorsQueryResponse[" +
                "author=" + author + ", " +
                "title=" + title + ", " +
                "pageNumber=" + pageNumber + ", " +
                "sortBy=" + sortBy + ", " +
                "sortOrder=" + sortOrder + ", " +
                "pageSize=" + pageSize + ']';
    }

}
