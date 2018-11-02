package seedu.address.model.group.util;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Group} matches any of the keywords given.
 * {@author jeffreyooi}
 */
public class GroupContainsPersonPredicate implements Predicate<Person> {
    private final List<Group> groups;

    public GroupContainsPersonPredicate(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean test(Person person) {
        return groups.stream().filter(group -> group.getMembersView().size() > 0)
            .anyMatch(group -> group.getMembersView().contains(person));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GroupContainsPersonPredicate
                && groups.equals(((GroupContainsPersonPredicate) other).groups));
    }
}
