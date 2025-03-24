package biblotech.mapper;

import biblotech.exceptions.InvalidSortOrderQueryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static biblotech.mapper.SortedBookOrderMapper.*;
import static org.assertj.core.api.Assertions.*;

class SortedBookOrderMapperTest {

    @Test
    @DisplayName("mapToOrderType invalid input throws exception")
    void mapToOrderTypeInvalidInputThrowsException() {


        assertThatThrownBy(() -> mapToOrderType("invalid"))
                .isInstanceOf(InvalidSortOrderQueryException.class)
                .hasMessageContaining("invalid");

    }

    @Test
    @DisplayName("Valid entered Uppercase argument is acceptable")
    void validEnteredUppercaseArgumentIsAcceptable() {
        var actual = mapToOrderType("ASC");
        assertThat(actual).isEqualTo("asc");

        var actual2 = mapToOrderType("DESC");
        assertThat(actual2).isEqualTo("desc");

    }
}
