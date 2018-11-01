package seedu.address.testutil;

import seedu.address.model.MeetingBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code MeetingBook ab = new MeetingBookBuilder().withPerson("John", "Doe").build();}
 */
public class MeetingBookBuilder {

    private MeetingBook addressBook;

    public MeetingBookBuilder() {
        addressBook = new MeetingBook();
    }

    public MeetingBookBuilder(MeetingBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code MeetingBook} that we are building.
     */
    public MeetingBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public MeetingBook build() {
        return addressBook;
    }
}
