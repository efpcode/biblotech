package biblotech.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.assertj.core.api.Assertions.*;
class ISBNMapperTest {

    @Test
    @DisplayName("ISBNMapper.mapToBook empty string return null")
    void isbnMapperMapToBookEmptyStringReturnNull() {
        var result = ISBNMapper.mapToBook("");
        assertThat(result).isNull();

    }

    @Test
    @DisplayName("ISBNMapper.mapToBook null returns null")
    void isbnMapperMapToBookNullReturnsNull() {
        var result = ISBNMapper.mapToBook(null);
        assertThat(result).isNull();


    }

    @Test
    @DisplayName("ISBNMapper.mapToBook digits and X are pickded out from string")
    void isbnMapperMapToBookDigitsAndXArePickdedOutFromString() {
        var result = ISBNMapper.mapToBook("aaa123aaaxX");
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("123XX");

    }







}
