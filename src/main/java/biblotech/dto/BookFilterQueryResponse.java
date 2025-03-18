package biblotech.dto;

import biblotech.rules.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

import java.util.Objects;
@ValidDateRange
public  class BookFilterQueryResponse {
    @QueryParam("title")
    private  String title;

    @QueryParam("author")
    private  String author;

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

    @QueryParam("startDate")
    private  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Expected date has invalid format: yyyy-MM-dd") String startDate;

    @QueryParam("endDate")
    private  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Expected format is:  yyyy-MM-dd") String endDate;

    public BookFilterQueryResponse(
    ) {
    }

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

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BookFilterQueryResponse) obj;
        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.author, that.author) &&
                Objects.equals(this.pageNumber, that.pageNumber) &&
                Objects.equals(this.sortBy, that.sortBy) &&
                Objects.equals(this.sortOrder, that.sortOrder) &&
                Objects.equals(this.pageSize, that.pageSize) &&
                Objects.equals(this.startDate, that.startDate) &&
                Objects.equals(this.endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, pageNumber, sortBy, sortOrder, pageSize, startDate, endDate);
    }

    @Override
    public String toString() {
        return "BookFilterQueryResponse[" +
                "title=" + title + ", " +
                "author=" + author + ", " +
                "pageNumber=" + pageNumber + ", " +
                "sortBy=" + sortBy + ", " +
                "sortOrder=" + sortOrder + ", " +
                "pageSize=" + pageSize + ", " +
                "startDate=" + startDate + ", " +
                "endDate=" + endDate + ']';
    }

}
