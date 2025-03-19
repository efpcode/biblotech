package biblotech.dto;

import biblotech.rules.*;
import jakarta.validation.constraints.*;

public record UpdateBook(
        @ValidBookTitle @NotBlank
        String title,
        @ValidBookAuthor @NotBlank
        String author,
        @ValidBookISBN @NotBlank
        String isbn,

        @ValidBookDescription
        String description,

        @ValidBookPublishedYear @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")  @NotNull
        String publishedYear,

        @ValidateBookPages @NotNull @Positive
        String pages
        ) {}
