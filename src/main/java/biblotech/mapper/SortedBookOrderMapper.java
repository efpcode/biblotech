package biblotech.mapper;

import biblotech.dto.SortedBookOrder;
import biblotech.exceptions.InvalidSortOrderQueryException;

import java.util.Arrays;

public class SortedBookOrderMapper {
    private  SortedBookOrderMapper() {

    }

    public static SortedBookOrder mapToOrderType(SortedBookOrder sortedBookOrder, String[] expectedOrder) {

        String order = sortedBookOrder.order().toLowerCase();
        if(order.isBlank())
            order = "asc";


        if(!Arrays.asList(expectedOrder).contains(order)) {
            throw new InvalidSortOrderQueryException("Invalid  sort   order: "+ sortedBookOrder.order()+"\n Expected values are" + Arrays.toString(expectedOrder));
        }

        return new SortedBookOrder(order);

    }
}
