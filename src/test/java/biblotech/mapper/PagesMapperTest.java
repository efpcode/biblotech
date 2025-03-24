package biblotech.mapper;

import biblotech.exceptions.InvalidBookPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.assertj.core.api.Assertions.*;

class PagesMapperTest {

    @Test
    @DisplayName("Empty page returns 0L")
    void emptyPageReturnsNullZeroL() {

        var results = PagesMapper.mapToLong("");
        assertThat(results).isEqualTo(0L);

    }

    @Test
    @DisplayName("Null page return Zero Long")
    void nullPageReturnZeroLong() {
        var results = PagesMapper.mapToLong(null);
        assertThat(results).isEqualTo(0L);

    }


    @Test
    @DisplayName("Values that are not digits throws exception")
    void valuesThatAreNotDigitsThrowsException() {

        assertThatThrownBy(() -> PagesMapper.mapToLong("VA"))
                .isInstanceOf(InvalidBookPage.class).hasMessage("Invalid page(s) input, please provide a valid page number");

    }


    @Test
    @DisplayName("Values that are digits are converted to Long")
    void valuesThatAreDigitsAreConvertedToLong() {
        var results = PagesMapper.mapToLong("123456789");
        assertThat(results).isEqualTo(123456789L);
        assertThat(results).isInstanceOf(Long.class);

    }

}
