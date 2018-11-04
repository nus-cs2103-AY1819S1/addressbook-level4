package seedu.restaurant.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.restaurant.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.UniqueAccountList;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.UniqueIngredientList;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.UniqueItemList;
import seedu.restaurant.model.menu.exceptions.ItemNotFoundException;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.UniqueReservationList;
import seedu.restaurant.model.salesrecord.Date;
import seedu.restaurant.model.salesrecord.SalesRecord;
import seedu.restaurant.model.salesrecord.SalesReport;
import seedu.restaurant.model.salesrecord.UniqueRecordList;
import seedu.restaurant.model.tag.Tag;

/**
 * Wraps all data at the restaurant book level. Duplicates are not allowed.
 */
public class RestaurantBook implements ReadOnlyRestaurantBook {

    private final UniqueReservationList reservations;
    private final UniqueRecordList records;
    private final UniqueAccountList accounts;
    private final UniqueIngredientList ingredients;
    private final UniqueItemList items;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        reservations = new UniqueReservationList();
        records = new UniqueRecordList();
        accounts = new UniqueAccountList();
        ingredients = new UniqueIngredientList();
        items = new UniqueItemList();
    }

    public RestaurantBook() {}

    /**
     * Creates an RestaurantBook using the data in the {@code toBeCopied}.
     */
    public RestaurantBook(ReadOnlyRestaurantBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code RestaurantBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRestaurantBook newData) {
        requireNonNull(newData);

        setReservations(newData.getReservationList());
        setRecords(newData.getRecordList());
        setAccounts(newData.getAccountList());
        setIngredients(newData.getIngredientList());
        setItems(newData.getItemList());
    }

    // Reservation Management

    //@@author m4dkip

    /**
     * Replaces the contents of the reservation list with {@code reservations}. {@code reservations} must not contain
     * duplicate reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations.setReservations(reservations);
    }

    /**
     * Returns true if a reservation with the same identity as {@code reservation} exists in the restaurant book.
     */
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reservations.contains(reservation);
    }

    /**
     * Adds a reservation to the restaurant book. The reservation must not already exist in the restaurant book.
     */
    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    /**
     * Replaces the given reservation {@code target} in the list with {@code editedReservation}. {@code target} must
     * exist in the restaurant book. The person identity of {@code editedReservation} must not be the same as another
     * existing person in the restaurant book.
     */
    public void updateReservation(Reservation target, Reservation editedReservation) {
        requireNonNull(editedReservation);

        reservations.setReservation(target, editedReservation);
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}. {@code key} must exist in the restaurant book.
     */
    public void removeReservation(Reservation key) {
        reservations.remove(key);
    }

    /**
     * Removes {@code tag} from all {@code item} in this {@code RestaurantBook}.
     *
     * @param tag to be removed.
     */
    public void removeTagForReservationList(Tag tag) {
        reservations.forEach(reservation -> removeTagForReservation(reservation, tag));
    }

    /**
     * Removes {@code tag} from {@code Reservation}.
     *
     * @param reservation whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForReservation(Reservation reservation, Tag tag) {
        Set<Tag> tags = new HashSet<>(reservation.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Reservation newReservation = new Reservation(reservation.getName(), reservation.getPax(),
                reservation.getDateTime(), reservation.getRemark(), tags);
        updateReservation(reservation, newReservation);
    }

    /**
     * Sorts the reservations list.
     */
    public void sortReservations() {
        reservations.sortReservations();
    }

    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
    }

    //// sales record-level operation

    /**
     * Replaces the contents of the record list with {@code records}. {@code records} must not contain duplicate
     * records.
     */
    public void setRecords(List<SalesRecord> records) {
        this.records.setRecords(records);
    }


    /**
     * Returns true if a record with the same identity as {@code record} exists in the sales book.
     */
    public boolean hasRecord(SalesRecord record) {
        requireNonNull(record);
        return records.contains(record);
    }

    /**
     * Adds a record to the restaurant book. The record must not already exist in the sales book.
     */
    public void addRecord(SalesRecord r) {
        records.add(r);
    }

    /**
     * Replaces the given record {@code target} in the list with {@code editedRecord}. {@code target} must exist in the
     * sales book. The record identity of {@code editedRecord} must not be the same as another existing record in the
     * sales book.
     */
    public void updateRecord(SalesRecord target, SalesRecord editedRecord) {
        requireNonNull(editedRecord);

        records.setRecord(target, editedRecord);
    }

    /**
     * Removes {@code key} from this {@code SalesBook}. {@code key} must exist in the sales book.
     */
    public void removeRecord(SalesRecord key) {
        records.remove(key);
    }

    @Override
    public ObservableList<SalesRecord> getRecordList() {
        return records.asUnmodifiableObservableList();
    }

    public SalesReport getSalesReport(Date date) {
        requireNonNull(date);
        return records.generateSalesReport(date);
    }

    //// account-level operations

    /**
     * Replaces the contents of the account list with {@code Account}s. {@code Account}s must not contain duplicate
     * accounts.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }

    /**
     * Returns true if a person with the same identity as {@code person} exists in the restaurant book.
     */
    public boolean hasAccount(Account account) {
        return accounts.contains(account);
    }

    /**
     * Returns the {@code Account} of the given {@code account}. That is, the account in storage is retrieved that
     * matches the {@code account} input given by the user.
     */
    public Account getAccount(Account account) {
        return accounts.get(account);
    }

    /**
     * Adds an account to the account record. The account must not already exist in the account record.
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Replaces the given account {@code target} in the list with {@code editedAccount}. {@code target} must exist in
     * the restaurant book. The account identity of {@code editedAccount} must not be the same as another existing
     * account in the restaurant book.
     */
    public void updateAccount(Account target, Account editedAccount) {
        accounts.update(target, editedAccount);
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}. {@code key} must exist in the restaurant book.
     */
    public void removeAccount(Account key) {
        accounts.remove(key);
    }

    @Override
    public ObservableList<Account> getAccountList() {
        return accounts.asUnmodifiableObservableList();
    }

    //// ingredient-level operations

    /**
     * Replaces the contents of the ingredient list with {@code ingredients}. {@code ingredients} must not contain
     * duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setIngredients(ingredients);
    }

    /**
     * Returns true if an ingredient with the same identity as {@code ingredient} exists in the restaurant book.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    /**
     * Finds an ingredient in the restaurant book with the name {@code IngredientName}. The ingredient must already
     * exist in the restaurant book.
     */
    public Ingredient findIngredient(IngredientName name) throws IngredientNotFoundException {
        return ingredients.find(name);
    }

    /**
     * Adds an ingredient to the restaurant book. The ingredient must not already exist in the restaurant book.
     */
    public void addIngredient(Ingredient i) {
        ingredients.add(i);
    }

    /**
     * Replaces the given ingredient {@code target} in the list with {@code editedIngredients}. {@code target} must
     * exist in the restaurant book. The ingredient identity of {@code editedIngredient} must not be the same as another
     * existing ingredient in the restaurant book.
     */
    public void updateIngredient(Ingredient target, Ingredient editedIngredient) {
        requireNonNull(editedIngredient);

        ingredients.setIngredient(target, editedIngredient);
    }


    /**
     * Increases the number of units of {@code Ingredient} by {@code Integer}. The ingredient key of HashMap {@code
     * requiredIngredients} must exist in the restaurant book.
     */
    public void stockUpIngredients(Map<IngredientName, Integer> requiredIngredients) {
        requireNonNull(requiredIngredients);

        ingredients.stockUp(requiredIngredients);
    }

    /**
     * Reduces the number of units of {@code Ingredient} by {@code Integer}. The ingredient key of HashMap {@code
     * requiredIngredients} must exist in the restaurant book.
     */
    public void consumeIngredients(Map<IngredientName, Integer> requiredIngredients) {
        requireNonNull(requiredIngredients);

        ingredients.consume(requiredIngredients);
    }

    /**
     * Removes ingredient with {@code key} from this {@code RestaurantBook}. {@code key} must exist in the restaurant
     * book.
     */
    public void removeIngredient(Ingredient key) {
        ingredients.remove(key);
    }

    @Override
    public ObservableList<Ingredient> getIngredientList() {
        return ingredients.asUnmodifiableObservableList();
    }

    // Menu Management
    //@@author yican95
    /**
     * Replaces the contents of the person list with {@code Item}s. {@code Item}s must not contain duplicate
     * items.
     */
    public void setItems(List<Item> items) {
        this.items.setItems(items);
    }

    /**
     * Returns true if a item with the same identity as {@code item} exists in the menu.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds an item to the menu. The item must not already exist in the menu.
     */
    public void addItem(Item i) {
        items.add(i);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}. {@code target} must exist in the
     * menu. The item identity of {@code editedItem} must not be the same as another existing item in the menu.
     */
    public void updateItem(Item target, Item editedItem) {
        requireNonNull(editedItem);

        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code Menu}. {@code key} must exist in the menu.
     */
    public void removeItem(Item key) {
        items.remove(key);
    }

    /**
     * Removes {@code tag} from {@code item} in this {@code Menu}.
     *
     * @param item whose tag is being removed.
     * @param tag to be removed.
     */
    private void removeTagForItem(Item item, Tag tag) {
        Set<Tag> tags = new HashSet<>(item.getTags());

        if (!tags.remove(tag)) {
            return;
        }

        Item newItem = new Item(item.getName(), item.getPrice(), item.getRecipe(), tags,
                item.getRequiredIngredients());
        updateItem(item, newItem);
    }

    /**
     * Removes {@code tag} from all {@code item} in this {@code RestaurantBook}.
     *
     * @param tag to be removed.
     */
    public void removeTagForMenu(Tag tag) {
        items.forEach(item -> removeTagForItem(item, tag));
    }

    /**
     * Resets the menu data of this {@code RestaurantBook} with {@code newData}.
     */
    public void resetMenuData(ReadOnlyRestaurantBook newData) {
        requireNonNull(newData);
        setItems(newData.getItemList());
    }

    /**
     * Sorts the menu by the given sorting method.
     */
    public void sortMenu(SortMethod sortMethod) {
        switch (sortMethod) {
        case NAME:
            items.sortItemsByName();
            return;
        case PRICE:
            items.sortItemsByPrice();
            return;
        default:
            return;
        }
    }

    /**
     * Finds an item in the menu with the name {@code Name}. The item must already exist in the menu.
     */
    public Item findItem(Name name) throws ItemNotFoundException {
        return items.find(name);
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    //// util methods
    //@@author
    @Override
    public String toString() {
        return String.valueOf(accounts.asUnmodifiableObservableList().size() + " accounts\n"
                + ingredients.asUnmodifiableObservableList().size() + " ingredients\n"
                + items.asUnmodifiableObservableList().size() + " items\n"
                + records.asUnmodifiableObservableList().size() + " records");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RestaurantBook)) {
            return false;
        }

        return accounts.equals(((RestaurantBook) other).accounts)
                && ingredients.equals(((RestaurantBook) other).ingredients)
                && items.equals(((RestaurantBook) other).items)
                && records.equals(((RestaurantBook) other).records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts, ingredients, items, records);
    }
}
