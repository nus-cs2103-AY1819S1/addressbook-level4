package seedu.address.testutil;

import static seedu.address.testutil.TypicalAssignments.getTypicalAssignment;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import seedu.address.model.AddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;

/**
 * A utility class containing an {@code Addressbook} objects to be used in tests.
 */
public class TypicalAddressbook {
    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Assignment assignment : getTypicalAssignment()) {
            ab.addAssignment(assignment);
        }
        return ab;
    }

    public static AddressBook getTypicalAddressBookCopy() {
        AddressBookBuilder abBuilder = new AddressBookBuilder();
        for (Person person : getTypicalPersons()) {
            PersonBuilder personBuilder = new PersonBuilder(person);
            abBuilder.withPerson(personBuilder.build());
        }
        for (Assignment assignment : getTypicalAssignment()) {
            AssignmentBuilder assignmentBuilder = new AssignmentBuilder(assignment);
            abBuilder.withAssignment(assignmentBuilder.build());
        }
        return abBuilder.build();
    }
}
