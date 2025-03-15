package biblotech.dto;


public record SortedBookPageResponse(BookListResponse sortedBooks, Integer totalElements, Long totalPages) {

    public SortedBookPageResponse(BookListResponse sortedBooks, Integer totalElements, Long totalPages) {
        this.sortedBooks = sortedBooks;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

}
