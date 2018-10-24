package seedu.parking.testutil;

import seedu.parking.model.CarparkFinder;
import seedu.parking.model.carpark.Carpark;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CarparkFinder ab = new AddressBookBuilder().withCarpark("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private CarparkFinder addressBook;

    public AddressBookBuilder() {
        addressBook = new CarparkFinder();
    }

    public AddressBookBuilder(CarparkFinder addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code CarparkFinder} that we are building.
     */
    public AddressBookBuilder withCarpark(Carpark carpark) {
        addressBook.addCarpark(carpark);
        return this;
    }

    public CarparkFinder build() {
        return addressBook;
    }
}
