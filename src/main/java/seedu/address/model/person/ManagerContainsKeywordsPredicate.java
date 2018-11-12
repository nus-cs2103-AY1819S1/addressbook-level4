package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Manager} matches any of the keywords given.
 */
public class ManagerContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ManagerContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getManager().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ManagerContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ManagerContainsKeywordsPredicate) other).keywords)); // state check
    }
}
