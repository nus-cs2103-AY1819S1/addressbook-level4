package seedu.address.testutil;

import seedu.address.model.HealthBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code HealthBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private HealthBook healthBook;

    public AddressBookBuilder() {
        healthBook = new HealthBook();
    }

    public AddressBookBuilder(HealthBook healthBook) {
        this.healthBook = healthBook;
    }

    /**
     * Adds a new {@code Person} to the {@code HealthBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        healthBook.addPerson(person);
        return this;
    }

    public HealthBook build() {
        return healthBook;
    }
}
