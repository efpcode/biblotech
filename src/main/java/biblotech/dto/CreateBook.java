package biblotech.dto;

import biblotech.rules.ValidBookAuthor;
import biblotech.rules.ValidBookISBN;
import biblotech.rules.ValidBookPublishDate;
import biblotech.rules.ValidBookTitle;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateBook(
        @ValidBookTitle String title,
        @ValidBookAuthor String author,
        @ValidBookISBN String isbn,
        @Size(min = 1, max = 1000, message = "Need a length Character greater than zero and smaller than thousand")
        @NotBlank(message = "Description cannot be blank or null")
        String description,
        @ValidBookPublishDate @JsonbDateFormat("yyyy-MM-dd") LocalDate bookPublishDate,
        Long bookPagesNumber) {
}
