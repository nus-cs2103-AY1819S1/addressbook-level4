package seedu.address.model.person.exceptions;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class ContactContainsTagPredicate implements Predicate<Person> {
    private final String keyword;

    public ContactContainsTagPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(tag -> tag.equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsTagPredicate // instanceof handles nulls
                && keyword.equals(((ContactContainsTagPredicate) other).keyword)); // state check
    }

}
