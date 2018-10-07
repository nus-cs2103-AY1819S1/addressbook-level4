package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.article.Article;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withArticle("John", "Doe").build();}
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
     * Adds a new {@code Article} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withArticle(Article article) {
        addressBook.addArticle(article);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
