package seedu.restaurant.testutil;

import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * A utility class to help with building RestaurantBook objects.
 * Example usage: {@code RestaurantBook ab = new RestaurantBookBuilder().withAccount(account).build();}
 */
public class RestaurantBookBuilder {

    private RestaurantBook restaurantBook;

    public RestaurantBookBuilder() {
        restaurantBook = new RestaurantBook();
    }

    public RestaurantBookBuilder(RestaurantBook restaurantBook) {
        this.restaurantBook = restaurantBook;
    }


    //@@author HyperionNKJ
    /**
     * Adds a new {@code Record} to the {@code RestaurantBook} that we are building.
     */
    public RestaurantBookBuilder withRecord(SalesRecord record) {
        restaurantBook.addRecord(record);
        return this;
    }

    /**
     * Adds a new {@code Account} to the {@code RestaurantBook} that we are building.
     */
    public RestaurantBookBuilder withAccount(Account account) {
        restaurantBook.addAccount(account);
        return this;
    }

    //@@author yican95
    /**
     * Adds a new {@code Item} to the {@code RestaurantBook} that we are building.
     */
    public RestaurantBookBuilder withItem(Item item) {
        restaurantBook.addItem(item);
        return this;
    }

    //@@author m4dkip
    /**
     * Adds a new {@code Reservation} to the {@code RestaurantBook} that we are building.
     */
    public RestaurantBookBuilder withReservation(Reservation reservation) {
        restaurantBook.addReservation(reservation);
        return this;
    }

    /**
     * Adds a new {@code Ingredient} to the {@code RestaurantBook} that we are building.
     */
    public RestaurantBookBuilder withIngredient(Ingredient ingredient) {
        restaurantBook.addIngredient(ingredient);
        return this;
    }

    public RestaurantBook build() {
        return restaurantBook;
    }
}
