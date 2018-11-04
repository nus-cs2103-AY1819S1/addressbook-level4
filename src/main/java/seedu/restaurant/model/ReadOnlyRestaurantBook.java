package seedu.restaurant.model;

import javafx.collections.ObservableList;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.salesrecord.SalesRecord;

/**
 * Unmodifiable view of an restaurant book
 */
public interface ReadOnlyRestaurantBook {

    /**
     * Returns an unmodifiable view of the records list. This list will not contain any duplicate records.
     */
    ObservableList<SalesRecord> getRecordList();

    /**
     * Returns an unmodifiable view of the accounts list. This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();

    /**
     * Returns an unmodifiable view of the ingredients list. This list will not contain any duplicate ingredients.
     */
    ObservableList<Ingredient> getIngredientList();

    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemList();

    // Reservation Management
    /**
     * Returns an unmodifiable view of the reservations list.
     * This list will not contain any duplicate reservations.
     */
    ObservableList<Reservation> getReservationList();

}
