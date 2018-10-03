package seedu.address.model.wish;

import java.util.function.Predicate;

/**
 * Tests if a {@code Wish} is completed.
 */
public class WishCompletedPredicate implements Predicate<Wish> {
    private final boolean isCompletedList;

    public WishCompletedPredicate(boolean isCompleted) {
        this.isCompletedList = isCompleted;
    }

    @Override
    public boolean test(Wish wish) {
        return wish.isWishCompleted() == this.isCompletedList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WishCompletedPredicate // instanceof handles nulls
                && isCompletedList == (((WishCompletedPredicate) other).isCompletedList)); // state check
    }
}
