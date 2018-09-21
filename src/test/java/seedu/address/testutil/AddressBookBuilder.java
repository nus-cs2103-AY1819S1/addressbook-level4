package seedu.address.testutil;

import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TaskManager ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TaskManager addressBook;

    public AddressBookBuilder() {
        addressBook = new TaskManager();
    }

    public AddressBookBuilder(TaskManager addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Task} to the {@code TaskManager} that we are building.
     */
    public AddressBookBuilder withPerson(Task person) {
        addressBook.addPerson(person);
        return this;
    }

    public TaskManager build() {
        return addressBook;
    }
}
