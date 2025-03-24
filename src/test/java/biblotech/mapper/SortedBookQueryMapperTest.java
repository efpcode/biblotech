package biblotech.mapper;

import biblotech.exceptions.InvalidSortByQueryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.assertj.core.api.Assertions.*;

class SortedBookQueryMapperTest {

    @Test
    @DisplayName("Incorrectly named field throws error")
    void incorrectlyNamedFieldThrowsError() {

        assertThatThrownBy(() -> SortedBookQueryMapper.mapToSortedBookQuery("invalid"))
                .isInstanceOf(InvalidSortByQueryException.class)
                .hasMessageContaining( "Invalid sort by: invalid"+".\n Valid values are:");
    }

    @Test
    @DisplayName("DTO Field conversion are predicted ")
    void dtoFieldConversionArePredicted() {

        assertThat(SortedBookQueryMapper.mapToSortedBookQuery("title")).isEqualTo("bookTitle");
        assertThat(SortedBookQueryMapper.mapToSortedBookQuery("author")).isEqualTo("bookAuthor");
        assertThat(SortedBookQueryMapper.mapToSortedBookQuery("pages")).isEqualTo("pages");
        assertThat(SortedBookQueryMapper.mapToSortedBookQuery("published")).isEqualTo("bookPublishDate");
        assertThat(SortedBookQueryMapper.mapToSortedBookQuery("description")).isEqualTo("bookDescription");
        assertThat(SortedBookQueryMapper.mapToSortedBookQuery("isbn")).isEqualTo("bookIsbn");

    }


}
