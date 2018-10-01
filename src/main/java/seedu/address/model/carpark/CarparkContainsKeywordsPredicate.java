package seedu.address.model.carpark;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.carpark.Carpark;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
<<<<<<< HEAD:src/main/java/seedu/address/model/carpark/CarparkContainsKeywordsPredicate.java
public class CarparkContainsKeywordsPredicate implements Predicate<Carpark> {
=======
public class NameContainsKeywordsPredicate implements Predicate<Carpark> {
>>>>>>> master:src/main/java/seedu/address/model/person/NameContainsKeywordsPredicate.java
    private final List<String> keywords;

    public CarparkContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Carpark carpark) {
        return keywords.stream()
<<<<<<< HEAD:src/main/java/seedu/address/model/carpark/CarparkContainsKeywordsPredicate.java
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        carpark.getCarparkNumber().value, keyword));
=======
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(carpark.getCarparkNumber().value, keyword));
>>>>>>> master:src/main/java/seedu/address/model/person/NameContainsKeywordsPredicate.java
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CarparkContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CarparkContainsKeywordsPredicate) other).keywords)); // state check
    }

}
