package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

public class ExistingPersonInEventPredicate implements Predicate<Person> {
    private final Set<Person> contacts;

    public ExistingPersonInEventPredicate(Set<Person> persons) {
        contacts = persons;
    }

    @Override
    public boolean test(Person person) {
        return contacts.stream().anyMatch(contact -> person.isSamePerson(contact));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExistingPersonInEventPredicate // instanceof handles nulls
                && contacts.equals(((ExistingPersonInEventPredicate) other).contacts)); // state check
    }

}
