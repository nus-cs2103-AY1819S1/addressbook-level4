package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Volunteer} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withVolunteer(Volunteer volunteer) {
        addressBook.addVolunteer(volunteer);
        return this;
    }

    /**
     * Adds a new {@code Event} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withEvent(Event event) {
        addressBook.addEvent(event);
        return this;
    }

    /**
     * Adds a new {@code Record} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withRecord(Record record) {
        addressBook.addRecord(record);
        return this;
    }


    public AddressBook build() {
        return addressBook;
    }
}
