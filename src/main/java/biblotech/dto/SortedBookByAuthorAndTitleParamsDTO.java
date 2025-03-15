package biblotech.dto;

public record SortedBookByAuthorAndTitleParamsDTO(Long pageNumber, Integer pageSize, String sortBy, String sortOrder) {

    public SortedBookByAuthorAndTitleParamsDTO(Long pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        if(pageSize == null || pageSize <= 0) {
            pageSize = 5;
        }
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = 1L;
        }
        if(sortBy == null || sortBy.isBlank()) {
            sortBy = "bookTitle";
        }
        if(sortOrder == null || sortOrder.isBlank()) {
            sortOrder = "asc";
        }
        if(sortBy.equalsIgnoreCase("title")) {
            sortBy = "bookTitle";

        }else if(sortBy.equalsIgnoreCase("author")) {
            sortBy = "bookAuthor";
        }
        if(sortOrder.equalsIgnoreCase("asc") || sortOrder.toLowerCase().contains("asc")) {
            sortOrder = "asc";
        }else if (sortOrder.equalsIgnoreCase("desc") || sortOrder.toLowerCase().contains("desc")) {
            sortOrder = "desc";
        }

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

}
