package seedu.address.testutil;

import seedu.address.model.WishBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code WishBook ab = new WishBookBuilder().withPerson("John", "Doe").build();}
 */
public class WishBookBuilder {

    private WishBook wishBook;

    public WishBookBuilder() {
        wishBook = new WishBook();
    }

    public WishBookBuilder(WishBook wishBook) {
        this.wishBook = wishBook;
    }

    /**
     * Adds a new {@code Person} to the {@code WishBook} that we are building.
     */
    public WishBookBuilder withPerson(Person person) {
        wishBook.addPerson(person);
        return this;
    }

    public WishBook build() {
        return wishBook;
    }
}
