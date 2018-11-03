package seedu.restaurant.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.restaurant.model.ReadOnlyRestaurantBook;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;
import seedu.restaurant.model.tag.Tag;

/**
 * Contains utility methods for populating {@code RestaurantBook} with sample data.
 */
public class SampleDataUtil {

    //@@author AZhiKai
    private static Account getRootAccount() {
        return new Account(new Username("root"), new Password("1122qq"));
    }

    public static ReadOnlyRestaurantBook getSampleRestaurantBook() {
        RestaurantBook defaultRestaurantBook = new RestaurantBook();
        defaultRestaurantBook.addAccount(getRootAccount());
        return defaultRestaurantBook;
    }

    //@@author
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
