package seedu.address.model.order;

import java.util.function.Predicate;

/**
 * Tests that a {@code Order}'s {@code Name} matches any of the keywords given.
 */
public class OrderNameContainsKeywordPredicate implements Predicate<Order> {
    private final String keyword;

    public OrderNameContainsKeywordPredicate(String name) {
        keyword = name.trim().toLowerCase();
    }

    @Override
    public boolean test(Order order) {
        return order.getName().fullName.toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderNameContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((OrderNameContainsKeywordPredicate) other).keyword)); // state check
    }
}
