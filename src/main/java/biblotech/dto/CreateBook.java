package biblotech.dto;

import biblotech.rules.*;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateBook(
        @ValidBookTitle @NotBlank
        String title,

        @ValidBookAuthor @NotBlank
        String author,

        @ValidBookISBN @NotBlank
        String isbn,

        @Size(min = 1, max = 1000, message = "Need a length Character greater than zero and smaller than thousand")
        @NotBlank(message = "Description cannot be blank or null")
        String description,

        @ValidBookPublishedYear
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
        @NotNull
        String publishedYear,

        @ValidateBookPages @Positive @NotNull
        String pages) {
}
