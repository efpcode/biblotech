package biblotech.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record SortedBookPageResponse(
        @NotNull BookListResponse sortedBooks,
        @Positive Integer totalElements,
        @Positive Long totalPages) {

}
