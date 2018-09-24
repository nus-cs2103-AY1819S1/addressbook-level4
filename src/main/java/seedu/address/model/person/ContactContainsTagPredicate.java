package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Tag} matches the keyword given.
 */
public class ContactContainsTagPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private Set<Tag> tags;

    public ContactContainsTagPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .noneMatch(keyword -> person.getTags().stream().noneMatch(key -> keyword.equals(key.toString())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsTagPredicate // instanceof handles nulls
                && keywords.equals(((ContactContainsTagPredicate) other).keywords)); // state check
    }

}
