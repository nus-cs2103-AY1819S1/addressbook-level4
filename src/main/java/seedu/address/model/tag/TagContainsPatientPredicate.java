package seedu.address.model.tag;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} contains "Patient".
 */
public class TagContainsPatientPredicate  implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return person.getTags().contains(new Tag("Patient"));
    }

    @Override
    public boolean equals(Object other){
        if (other == this) { //if same object
            return true;
        } else {
            return false;
        }
    }

}
