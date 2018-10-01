package seedu.address.model.tag;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

//@@author A19Sean
/**
 * Tests that a {@code Person} contains any of the tags given.
 */
public class PersonContainsTagPredicate implements Predicate<Person> {
    private final List<String> tags;

    public PersonContainsTagPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return tags.stream()
                .anyMatch(tagName -> {
                    Tag tag = new Tag(tagName);
                    return person.getTags().contains(tag);
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagPredicate // instanceof handles nulls
                && tags.equals(((PersonContainsTagPredicate) other).tags)); // state check
    }

}
