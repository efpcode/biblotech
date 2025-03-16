package biblotech.dto;


import jakarta.validation.constraints.NotBlank;

public record SortedBookQuery(@NotBlank String sortBy){}
