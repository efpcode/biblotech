package biblotech.mapper;

import biblotech.exceptions.InvalidSortOrderQueryException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SortedBookOrderMapper {
    private  SortedBookOrderMapper() {

    }

    private static final Set<String> SORT_ORDER_FIELDS = Set.of("asc","desc");

    public static String mapToOrderType(String sortedBookOrder) {
        String order = sortedBookOrder.trim().toLowerCase();

        if(!SORT_ORDER_FIELDS.contains(order)) {
            throw new InvalidSortOrderQueryException("Invalid  sort   order: "+ order+"\n Expected values are" + (SORT_ORDER_FIELDS));
        }

        return order;

    }
}
