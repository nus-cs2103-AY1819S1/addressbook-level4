package seedu.address.testutil;

import seedu.address.model.WishBook;
import seedu.address.model.wish.Wish;

/**
 * A utility class to help with building WishBook objects.
 * Example usage: <br>
 *     {@code WishBook ab = new WishBookBuilder().withWish("John", "Doe").build();}
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
     * Adds a new {@code Wish} to the {@code WishBook} that we are building.
     */
    public WishBookBuilder withWish(Wish wish) {
        wishBook.addWish(wish);
        return this;
    }

    public WishBook build() {
        return wishBook;
    }
}
