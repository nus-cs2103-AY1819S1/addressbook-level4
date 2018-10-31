package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Interest} matches the keyword given.
 */
public class InterestContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public InterestContainsKeywordsPredicate(String keywords) {
        this.keyword = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getInterests().stream()
                                    .map(interest -> interest.interestName)
                                    .anyMatch(interestName -> StringUtil.containsWordIgnoreCase(keyword, interestName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterestContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((InterestContainsKeywordsPredicate) other).keyword)); // state check
    }

}
