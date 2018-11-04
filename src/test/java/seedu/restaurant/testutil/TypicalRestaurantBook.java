package seedu.restaurant.testutil;

import static seedu.restaurant.testutil.account.TypicalAccounts.getTypicalAccounts;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.getTypicalIngredients;
import static seedu.restaurant.testutil.menu.TypicalItems.getTypicalItems;
import static seedu.restaurant.testutil.reservation.TypicalReservations.getTypicalReservations;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.getTypicalRecords;

import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.salesrecord.SalesRecord;

/**
 * A utility class containing a list of all possible types of an {@code RestaurantBook} objects to be used in tests.
 */
public class TypicalRestaurantBook {

    /**
     * Returns an {@code RestaurantBook} with all the typical objects it can possibly represent.
     */
    public static RestaurantBook getTypicalRestaurantBook() {
        RestaurantBook ab = new RestaurantBook();
        for (Item item : getTypicalItems()) {
            ab.addItem(item);
        }
        for (SalesRecord record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        for (Account account : getTypicalAccounts()) {
            ab.addAccount(account);
        }
        for (Ingredient ingredient : getTypicalIngredients()) {
            ab.addIngredient(ingredient);
        }
        for (Reservation reservation : getTypicalReservations()) {
            ab.addReservation(reservation);
        }
        return ab;
    }

    /**
     * Returns an {@code RestaurantBook} with {@code SalesRecord} objects only.
     */
    public static RestaurantBook getTypicalRestaurantBookWithRecordOnly() {
        RestaurantBook ab = new RestaurantBook();
        for (SalesRecord record : getTypicalRecords()) {
            ab.addRecord(record);
        }
        return ab;
    }

    /**
     * Returns an {@code RestaurantBook} with {@code Account} objects only.
     */
    public static RestaurantBook getTypicalRestaurantBookWithAccountsOnly() {
        RestaurantBook ab = new RestaurantBook();
        for (Account account : getTypicalAccounts()) {
            ab.addAccount(account);
        }
        return ab;
    }

    /**
     * Returns an {@code RestaurantBook} with {@code Ingredient} objects only.
     */
    public static RestaurantBook getTypicalRestaurantBookWithIngredientsOnly() {
        RestaurantBook ab = new RestaurantBook();
        for (Ingredient ingredient : getTypicalIngredients()) {
            ab.addIngredient(ingredient);
        }
        return ab;
    }

    //@@author yican95
    /**
     * Returns an {@code RestaurantBook} with {@code Item} objects only.
     */
    public static RestaurantBook getTypicalRestaurantBookWithItemOnly() {
        RestaurantBook ab = new RestaurantBook();
        for (Item item : getTypicalItems()) {
            ab.addItem(item);
        }
        return ab;
    }

    //@@author m4dkip
    /**
     * Returns an {@code RestaurantBook} with {@code Reservation} objects only.
     */
    public static RestaurantBook getTypicalRestaurantBookWithReservationsOnly() {
        RestaurantBook ab = new RestaurantBook();
        for (Reservation reservation : getTypicalReservations()) {
            ab.addReservation(reservation);
        }
        return ab;
    }
}
