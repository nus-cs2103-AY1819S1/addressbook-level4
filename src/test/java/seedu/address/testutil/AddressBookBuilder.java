package seedu.address.testutil;

import seedu.address.model.AppContent;
import seedu.address.model.recipe.Recipe;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AppContent ab = new AddressBookBuilder().withRecipe("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AppContent addressBook;

    public AddressBookBuilder() {
        addressBook = new AppContent();
    }

    public AddressBookBuilder(AppContent addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Recipe} to the {@code AppContent} that we are building.
     */
    public AddressBookBuilder withRecipe(Recipe recipe) {
        addressBook.addRecipe(recipe);
        return this;
    }

    public AppContent build() {
        return addressBook;
    }
}
