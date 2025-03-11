package dto;

import rules.ValidBookTitle;

import java.time.LocalDate;

public record CreateBook(
       @ValidBookTitle String title,
        String author,
        String isbn,
        String description,
        LocalDate bookPublishDate,
        Long bookPagesNumber) {
}
