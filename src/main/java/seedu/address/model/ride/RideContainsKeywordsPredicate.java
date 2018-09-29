package seedu.address.model.ride;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Ride}'s attributes  matches any of the keywords given.
 */
public class RideContainsKeywordsPredicate implements Predicate<Ride> {
    private final List<String> keywords;
    private Optional<Address> addressKeyWords;

    public RideContainsKeywordsPredicate(List<String> keywords, Optional... other) {
        this.keywords = keywords;
        addressKeyWords = Optional.empty();
        getKeyWords(other);
    }

    /**
     * Get other keywords
     * @param others
     */
    private void getKeyWords(Optional... others) {
        for (Optional keywords : others) {
            if (keywords.isPresent()) {
                if (keywords.get().getClass().equals(Address.class)) {
                    addressKeyWords = keywords;
                }
            }
        }
    }

    @Override
    public boolean test(Ride ride) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    boolean result = StringUtil.containsWordIgnoreCase(ride.getName().fullName, keyword);
                    if (addressKeyWords.isPresent()) {
                        result = result || StringUtil.containsWordIgnoreCase(ride.getAddress().value,
                                addressKeyWords.get().value);
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
