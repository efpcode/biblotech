package biblotech.mapper;

import biblotech.dto.SortedBookOrder;
import biblotech.exceptions.InvalidSortOrderQueryException;

import java.util.Arrays;

public class SortedBookOrderMapper {
    private  SortedBookOrderMapper() {

    }

    static private String[] SORT_ORDER_FIELDS = {"asc","desc"};

    public static SortedBookOrder mapToOrderType(SortedBookOrder sortedBookOrder) {
        String order = sortedBookOrder.order().toLowerCase();

        if(!Arrays.asList(SORT_ORDER_FIELDS).contains(order)) {
            throw new InvalidSortOrderQueryException("Invalid  sort   order: "+ sortedBookOrder.order()+"\n Expected values are" + Arrays.toString(SORT_ORDER_FIELDS));
        }

        return new SortedBookOrder(order);

    }
}
