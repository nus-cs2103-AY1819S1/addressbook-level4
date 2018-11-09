package seedu.restaurant.model;

import java.util.Map;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.restaurant.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.model.sales.SalesReport;
import seedu.restaurant.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Account> PREDICATE_SHOW_ALL_ACCOUNTS = unused -> true;
    Predicate<Ingredient> PREDICATE_SHOW_ALL_INGREDIENTS = unused -> true;
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;
    Predicate<Reservation> PREDICATE_SHOW_ALL_RESERVATIONS = unused -> true;
    Predicate<SalesRecord> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data for RestaurantBook.
     */
    void resetData(ReadOnlyRestaurantBook newData);

    /**
     * Returns the RestaurantBook
     */
    ReadOnlyRestaurantBook getRestaurantBook();

    /**
     * Resets the version of the RestaurantBook
     */
    void resetRestaurantBookVersion();

    //=========== API for Sales =============================================================

    /**
     * Adds the given record. {@code record} must not already exist in the sales book.
     */
    void addRecord(SalesRecord record);

    /**
     * Returns true if a record with the same identity as {@code record} exists in the sales book.
     */
    boolean hasRecord(SalesRecord record);

    /**
     * Deletes the given record. The record must exist in the sales book.
     */
    void deleteRecord(SalesRecord target);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}. {@code target} must exist in the sales book.
     * The record identity of {@code editedRecord} must not be the same as another existing record in the sales book.
     */
    void updateRecord(SalesRecord target, SalesRecord editedRecord);

    /**
     * Returns the sales report of the specified date.
     */
    SalesReport getSalesReport(Date date);

    /**
     * Ranks the existing records' dates according to revenue in descending order.
     */
    Map<Date, Double> rankDateBasedOnRevenue();

    /**
     * Ranks the existing records' items according to revenue accumulated from past sales records in descending order.
     */
    Map<ItemName, Double> rankItemBasedOnRevenue();

    /**
     * Returns all dates, each associated with its total revenue for the day, in chronological order.
     */
    Map<Date, Double> getChronologicalSalesData();


    /**
     * Returns an unmodifiable view of the filtered record list
     */
    ObservableList<SalesRecord> getFilteredRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<SalesRecord> predicate);

    // =========== API for Reservations =============================================================

    //@@author m4dkip
    /**
     * Returns true if a reservation with the same identity as {@code reservation} exists in the restaurant book.
     */
    boolean hasReservation(Reservation reservation);

    /**
     * Deletes the given reservation. The reservation must exist in the restaurant book.
     */
    void deleteReservation(Reservation target);

    /**
     * Adds the given reservation. {@code reservation} must not already exist in the restaurant book.
     */
    void addReservation(Reservation reservation);

    /**
     * Replaces the given reservation {@code target} with {@code editedReservation}. {@code target} must exist in the
     * restaurant book. The reservation identity of {@code editedReservation} must not be the same as another existing
     * reservation in the restaurant book.
     */
    void updateReservation(Reservation target, Reservation editedReservation);

    /**
     * Removes the given {@code tag} from all {@code Reservation}
     *
     * @param tag to be removed.
     */
    void removeTagForReservation(Tag tag);

    /**
     * Sort the reservations list.
     */
    void sortReservations();

    /**
     * Returns an unmodifiable view of the filtered reservation list
     */
    ObservableList<Reservation> getFilteredReservationList();

    /**
     * Updates the filter of the filtered reservation list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReservationList(Predicate<Reservation> predicate);

    //=========== API for Accounts =============================================================

    //@@author AZhiKai
    /**
     * Adds the given account. {@code account} must not already exist in the account storage.
     *
     * @param account to be added.
     */
    void addAccount(Account account);

    /**
     * Retrieve the account. {@code account} must already exist in the account storage.
     *
     * @param account to retrieve.
     */
    Account getAccount(Account account);

    /**
     * Returns true if an account with the same identity as {@code Account} exists in the restaurant book.
     */
    boolean hasAccount(Account account);

    /**
     * Deletes the given account. The account must exist in the restaurant book.
     *
     * @param account to be removed.
     */
    void removeAccount(Account account);

    /**
     * Replaces the given account {@code target} with {@code editedAccount}. {@code target} must exist in the restaurant
     * book. The account identity of {@code editedAccount} must not be the same as another existing account in the
     * restaurant book.
     *
     * @param target account to be updated.
     * @param editedAccount updated account.
     */
    void updateAccount(Account target, Account editedAccount);

    ObservableList<Account> getFilteredAccountList();

    void updateFilteredAccountList(Predicate<Account> predicate);

    //=============== API for Ingredient ===============

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the restaurant book.
     */
    boolean hasIngredient(Ingredient ingredient);

    /**
     * Deletes the given ingredient. The ingredient must exist in the restaurant book.
     */
    void deleteIngredient(Ingredient target);

    /**
     * Adds the given ingredient. {@code ingredient} must not already exist in the restaurant book.
     */
    void addIngredient(Ingredient ingredient);

    /**
     * Finds the ingredient with the given ingredient name. The ingredient with {@code IngredientName} must already
     * exist in the restaurant book.
     */
    Ingredient findIngredient(IngredientName ingredientName);

    /**
     * Replaces the given ingredient {@code target} with {@code editedIngredient}. {@code target} must exist in the
     * restaurant book. The ingredient identity of {@code editedIngredient} must not be the same as another existing
     * ingredient in the restaurant book.
     */
    void updateIngredient(Ingredient target, Ingredient editedIngredient);

    /**
     * Stocks up a list of ingredients. For each ingredient, the number of units of {@code Ingredient} is increased by
     * {@Integer}. The ingredient key of Map {@code requiredIngredients} must exist in the restaurant book.
     */
    void stockUpIngredients(Map<IngredientName, Integer> requiredIngredients);

    /**
     * Consumes a list of ingredients. For each ingredient, the number of units of {@code Ingredient} is decreased by
     * {@Integer}. The ingredient key of Map {@code requiredIngredients} must exist in the restaurant book.
     */
    void consumeIngredients(Map<IngredientName, Integer> requiredIngredients);

    /**
     * Returns an unmodifiable view of the filtered ingredient list
     */
    ObservableList<Ingredient> getFilteredIngredientList();

    /**
     * Updates the filter of the filtered ingredient list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIngredientList(Predicate<Ingredient> predicate);

    //=============== API for Menu ===============
    //@@author yican95
    /**
     * Returns true if an item with the same identity as {@code item} exists in the restaurant book.
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given item. The item must exist in the restaurant book.
     */
    void deleteItem(Item target);

    /**
     * Adds the given item. {@code item} must not already exist in the restaurant book.
     */
    void addItem(Item item);

    /**
     * Replaces the given item {@code target} with {@code editedItem}. {@code target} must exist in the restaurant book.
     * The item identity of {@code editedItem} must not be the same as another existing item in the restaurant book.
     */
    void updateItem(Item target, Item editedItem);

    /**
     * Removes the given {@code tag} from all {@code Item}
     *
     * @param tag to be removed.
     */
    void removeTagForMenu(Tag tag);

    /**
     * Clears the item list and replaces with the provided new data for RestaurantBook.
     */
    void resetMenuData(ReadOnlyRestaurantBook newData);

    /**
     * Sort the item list by given sorting method.
     */
    void sortMenu(SortMethod sortMethod);

    /**
     * Finds the item with the given name. The item with {@code Name} must already exist in the menu.
     */
    Item findItem(Name name);

    /**
     * Returns an unmodifiable map of requiredIngredients of an {@code Item}
     */
    Map<IngredientName, Integer> getRequiredIngredients(Item item);

    /**
     * Returns an unmodifiable view of the filtered item list
     */
    ObservableList<Item> getFilteredItemList();

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemList(Predicate<Item> predicate);
    //@@author
    //=========== API for Redo/Undo =============================================================

    /**
     * Returns true if the model has previous restaurant book states to restore.
     */
    boolean canUndoRestaurantBook();

    /**
     * Returns true if the model has undone restaurant book states to restore.
     */
    boolean canRedoRestaurantBook();

    /**
     * Restores the model's restaurant book to its previous state.
     */
    void undoRestaurantBook();

    /**
     * Restores the model's restaurant book to its previously undone state.
     */
    void redoRestaurantBook();

    /**
     * Saves the current restaurant book state for undo/redo.
     */
    void commitRestaurantBook();
}
