package dto;

import rules.ValidBookISBN;
import rules.ValidBookTitle;

import java.time.LocalDate;

public record CreateBook(
       @ValidBookTitle String title,
        String author,
        @ValidBookISBN String isbn,
        String description,
        LocalDate bookPublishDate,
        Long bookPagesNumber) {
}
