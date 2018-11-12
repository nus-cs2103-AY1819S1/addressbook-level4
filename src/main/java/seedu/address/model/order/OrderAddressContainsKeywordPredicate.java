package seedu.address.model.order;

import java.util.function.Predicate;

/**
 * Tests that a {@code Order}'s {@code Address} matches any of the keywords given.
 */
public class OrderAddressContainsKeywordPredicate implements Predicate<Order> {
    private final String keyword;

    public OrderAddressContainsKeywordPredicate(String address) {
        keyword = address.trim().toLowerCase();
    }

    @Override
    public boolean test(Order order) {
        return order.getAddress().value.toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderAddressContainsKeywordPredicate // instanceof handles nulls
                && keyword.equals(((OrderAddressContainsKeywordPredicate) other).keyword)); // state check
    }

}
