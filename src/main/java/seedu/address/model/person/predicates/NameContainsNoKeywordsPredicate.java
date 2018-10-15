package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches none of the keywords given.
 */
public class NameContainsNoKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsNoKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
            .noneMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NameContainsNoKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((NameContainsNoKeywordsPredicate) other).keywords)); // state check
    }
}
