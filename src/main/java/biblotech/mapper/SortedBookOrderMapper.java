package biblotech.mapper;

import biblotech.exceptions.InvalidSortOrderQueryException;

import java.util.Arrays;

public class SortedBookOrderMapper {
    private  SortedBookOrderMapper() {

    }

    static private String[] SORT_ORDER_FIELDS = {"asc","desc"};

    public static String mapToOrderType(String sortedBookOrder) {
        String order = sortedBookOrder.toLowerCase();

        if(!Arrays.asList(SORT_ORDER_FIELDS).contains(order)) {
            throw new InvalidSortOrderQueryException("Invalid  sort   order: "+ order+"\n Expected values are" + Arrays.toString(SORT_ORDER_FIELDS));
        }

        return order;

    }
}
