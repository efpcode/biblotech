package dto;

import java.util.List;

public record BookListResponse(List<BookResponse> allBooks) {
}
