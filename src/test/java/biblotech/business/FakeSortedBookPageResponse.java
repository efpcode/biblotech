package biblotech.business;


import biblotech.dto.BookListResponse;
import biblotech.dto.SortedBookPageResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FakeSortedBookPageResponse(
        @NotNull BookListResponse sortedBooks,
        @Positive Integer totalElements,
        @Positive Long totalPages
        ){

    public static FakeSortedBookPageResponse createForTest(BookListResponse sortedBooks, Integer totalElements, Long totalPages){
        return new FakeSortedBookPageResponse(sortedBooks, totalElements, totalPages);
    }

    public static SortedBookPageResponse maptoPageResponse(BookListResponse sortedBooks,  Integer totalElements, Long totalPages){
        return new SortedBookPageResponse(sortedBooks,  totalElements, totalPages);
    }


}
