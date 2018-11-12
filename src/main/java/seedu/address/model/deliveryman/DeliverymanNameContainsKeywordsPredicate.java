package seedu.address.model.deliveryman;

import java.util.function.Predicate;

/**
 * Tests that a {@code Deliveryman}'s {@code Name} matches any of the keywords given.
 */
public class DeliverymanNameContainsKeywordsPredicate implements Predicate<Deliveryman> {
    private final String keyword;

    public DeliverymanNameContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.trim().toLowerCase();
    }

    @Override
    public boolean test(Deliveryman deliveryman) {
        return deliveryman.getName().fullName.toLowerCase().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliverymanNameContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((DeliverymanNameContainsKeywordsPredicate) other).keyword)); // state check
    }
}
