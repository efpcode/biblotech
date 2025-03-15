package biblotech.dto;

import java.util.List;

public record BookListResponse(List<BookResponse> allBooks) {
    public BookListResponse(List<BookResponse> allBooks) {
        this.allBooks = allBooks;
    }


}
