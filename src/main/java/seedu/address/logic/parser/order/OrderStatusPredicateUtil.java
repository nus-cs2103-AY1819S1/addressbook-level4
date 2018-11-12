package seedu.address.logic.parser.order;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderStatus;

/**
 * Parses of status keywords and returns a valid status list
 */
public class OrderStatusPredicateUtil {

    /**
     * Parses a list of {@code stringStatus} and returns a list of OrderStatus object
     * @throws ParseException if invalid status is supplied
     */
    public List<OrderStatus> parseOrderStatusKeywords(List<String> stringStatuses) throws ParseException {
        List<OrderStatus> statuses = new ArrayList<>();

        for (String stringStatus : stringStatuses) {
            String upperCaseStringStatus = stringStatus.toUpperCase();

            if (!OrderStatus.isValidStatus(upperCaseStringStatus)) {
                throw new ParseException(OrderStatus.MESSAGE_STATUS_CONSTRAINTS);
            }

            statuses.add(new OrderStatus(upperCaseStringStatus));
        }

        return statuses;
    }
}
