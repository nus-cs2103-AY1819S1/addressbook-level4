package seedu.address.model.order;

import java.util.Date;
import java.util.function.Predicate;


/**
 * Tests that a {@code Order}'s {@code Date} matches any of the keywords given.
 */
public class OrderDateSinglePredicate implements Predicate<Order> {
    private final Date keyword;

    public OrderDateSinglePredicate(Date date) {
        keyword = date;
    }

    @Override
    public boolean test(Order order) {
        return order.getDate().getDate().equals(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderDateSinglePredicate // instanceof handles nulls
                && keyword.equals(((OrderDateSinglePredicate) other).keyword)); // state check
    }
}
