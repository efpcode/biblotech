package biblotech.dto;

import jakarta.validation.constraints.Positive;

public record BookPagination(
        @Positive(message = "Page number input cannot be zero or less")
        Long pageNumber,
        @Positive(message = "Page size value cannot be zero or less")
        Integer pageSize) {


        public static BookPagination valueOf(Long pageNumber, Integer pageSize) {
                if (pageNumber == null  || pageNumber <= 0)
                        pageNumber = 1L;

                if (pageSize == null  || pageSize <= 0)
                        pageSize = 5;

                return new BookPagination(pageNumber, pageSize);

        }
}
