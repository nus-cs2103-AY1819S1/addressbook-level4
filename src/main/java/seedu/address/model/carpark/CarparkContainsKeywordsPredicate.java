package seedu.address.model.carpark;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Carpark}'s {@code carparkNumber} matches any of the keywords given.
 */
public class CarparkContainsKeywordsPredicate implements Predicate<Carpark> {
    private final List<String> keywords;

    public CarparkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Carpark carpark) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(carpark.getCarparkNumber().value, keyword)
                                || StringUtil.containsWordIgnoreCase(carpark.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CarparkContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CarparkContainsKeywordsPredicate) other).keywords)); // state check
    }

}
