package seedu.address.model.person;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Group} matches any of the keywords given.
 * {@author jeffreyooi}
 */
public class GroupContainsPersonPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public GroupContainsPersonPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<String> groupNames = person.getGroupTags().stream().map(Tag::getTagName).collect(Collectors.toSet());
        return !Collections.disjoint(groupNames, keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GroupContainsPersonPredicate
                && keywords.equals(((GroupContainsPersonPredicate) other).keywords));
    }
}
