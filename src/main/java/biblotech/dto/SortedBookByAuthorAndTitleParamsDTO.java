package biblotech.dto;

public record SortedBookByAuthorAndTitleParamsDTO(Long pageNumber, Integer pageSize, String sortBy, String sortOrder) {

    public SortedBookByAuthorAndTitleParamsDTO(Long pageNumber, Integer pageSize, String sortBy, String sortOrder) {

            this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

}
