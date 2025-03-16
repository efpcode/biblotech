package biblotech.dto;

import jakarta.validation.constraints.NotBlank;

public record SortedBookOrder(@NotBlank String order) {
}
