package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.carpark.Carpark;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withCarpark("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withCarpark(Carpark carpark) {
        addressBook.addCarpark(carpark);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
