package seedu.address.testutil;

import seedu.address.model.SchedulePlanner;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code SchedulePlanner ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private SchedulePlanner addressBook;

    public AddressBookBuilder() {
        addressBook = new SchedulePlanner();
    }

    public AddressBookBuilder(SchedulePlanner addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Task} to the {@code SchedulePlanner} that we are building.
     */
    public AddressBookBuilder withPerson(Task task) {
        addressBook.addTask(task);
        return this;
    }

    public SchedulePlanner build() {
        return addressBook;
    }
}
