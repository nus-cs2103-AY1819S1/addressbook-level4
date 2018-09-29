package seedu.address.model.ride;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Ride}'s attributes  matches any of the keywords given.
 */
public class RideContainsKeywordsPredicate implements Predicate<Ride> {
    private final List<String> keywords;
    private final boolean hasAddress;

    public RideContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.hasAddress = false;
    }

    public RideContainsKeywordsPredicate(List<String> keywords, boolean hasAddress) {
        this.keywords = keywords;
        this.hasAddress = hasAddress;
    }

    @Override
    public boolean test(Ride ride) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    boolean result = StringUtil.containsWordIgnoreCase(ride.getName().fullName, keyword);
                    if (this.hasAddress) {
                        result = result || StringUtil.containsWordIgnoreCase(ride.getAddress().toString(), keyword);
                    }
                    return result;
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RideContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RideContainsKeywordsPredicate) other).keywords)); // state check
    }

}
