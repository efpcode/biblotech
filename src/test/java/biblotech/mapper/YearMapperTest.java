package biblotech.mapper;

import static org.junit.jupiter.api.Assertions.*;
import biblotech.mapper.YearMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class YearMapperTest {

    @Test
    @DisplayName("getYearFromString called with null returns null")
    void getYearFromStringCalledWithNullReturnsNull() {

       var actual = YearMapper.getYearFromString(null);
       assertThat(actual).isNull();

    }

    @Test
    @DisplayName("Invalid or malformed date throws exception")
    void invalidOrMalformedDateThrowsException() {
        assertThatThrownBy(() -> YearMapper.getYearFromString("invalid"))
                .hasMessageContaining("Year could not be parsed");

    }


    @Test
    @DisplayName("Valid and correctly formatted year return LocalDate")
    void validAndCorrectlyFormattedYearReturnLocalDate() {
        var actual = YearMapper.getYearFromString("1999-01-12");
        assertThat(actual).isNotNull();
        assertThat(actual.getYear()).isEqualTo(1999);
        assertThat(actual.getMonth().getValue()).isEqualTo(1);
        assertThat(actual.getDayOfMonth()).isEqualTo(12);
        assertThat(actual).isInstanceOf(LocalDate.class);

    }



}
