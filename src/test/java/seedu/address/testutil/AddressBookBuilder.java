package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withRecipe("John", "Doe").build();}
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
     * Adds a new {@code Recipe} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withRecipe(Recipe recipe) {
        addressBook.addRecipe(recipe);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
