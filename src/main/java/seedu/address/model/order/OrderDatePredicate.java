package seedu.address.model.order;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Order}'s {@code Date} matches any of the keywords given.
 */
public class OrderDatePredicate implements Predicate<Order> {
    public static final int VALID_SEARCH_DATE_RANGE_SIZE = 2;
    public static final int LOWER_DATE_RANGE_INDEX = 0;
    public static final int UPPER_DATE_RANGE_INDEX = 1;

    private Predicate<Order> datePredicate;

    public OrderDatePredicate(List<Date> dates) {
        assert dates.size() < 3;

        if (dates.size() == VALID_SEARCH_DATE_RANGE_SIZE) {
            Date lowerDateBoundary = dates.get(LOWER_DATE_RANGE_INDEX);
            Date upperDateBoundary = dates.get(UPPER_DATE_RANGE_INDEX);
            assert (lowerDateBoundary.before(upperDateBoundary) && lowerDateBoundary != upperDateBoundary);
            datePredicate = new OrderDateRangePredicate(lowerDateBoundary, upperDateBoundary);
        } else {
            // One date in list
            datePredicate = new OrderDateSinglePredicate(dates.get(0));
        }
    }

    @Override
    public boolean test(Order order) {
        return datePredicate.test(order);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderDatePredicate // instanceof handles nulls
                && datePredicate.equals(((OrderDatePredicate) other).datePredicate)); // state check
    }
}
