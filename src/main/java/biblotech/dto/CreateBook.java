package biblotech.dto;

import biblotech.rules.ValidBookISBN;
import biblotech.rules.ValidBookTitle;

import java.time.LocalDate;

public record CreateBook(
       @ValidBookTitle String title,
        String author,
        @ValidBookISBN String isbn,
        String description,
        LocalDate bookPublishDate,
        Long bookPagesNumber) {
}
