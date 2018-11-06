package seedu.restaurant.model;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.restaurant.commons.core.ComponentManager;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.events.model.RestaurantBookChangedEvent;
import seedu.restaurant.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.exceptions.ItemNotFoundException;
import seedu.restaurant.model.person.Person;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.model.sales.SalesReport;
import seedu.restaurant.model.tag.Tag;

/**
 * Represents the in-memory model of the restaurant book data.
 */
public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedRestaurantBook versionedRestaurantBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Reservation> filteredReservations;
    private final FilteredList<SalesRecord> filteredRecords;
    private final FilteredList<Account> filteredAccounts;
    private final FilteredList<Ingredient> filteredIngredients;
    private final FilteredList<Item> filteredItems;

    /**
     * Initializes a ModelManager with the given {@code restaurantBook} and {@code userPrefs}.
     */
    public ModelManager(ReadOnlyRestaurantBook restaurantBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(restaurantBook, userPrefs);

        logger.fine("Initializing with restaurant book: " + restaurantBook + " and user prefs " + userPrefs);

        versionedRestaurantBook = new VersionedRestaurantBook(restaurantBook);
        filteredPersons = new FilteredList<>(versionedRestaurantBook.getPersonList());
        filteredReservations = new FilteredList<>(versionedRestaurantBook.getReservationList());
        filteredRecords = new FilteredList<>(versionedRestaurantBook.getRecordList());
        filteredAccounts = new FilteredList<>(versionedRestaurantBook.getAccountList());
        filteredIngredients = new FilteredList<>(versionedRestaurantBook.getIngredientList());
        filteredItems = new FilteredList<>(versionedRestaurantBook.getItemList());
    }

    public ModelManager() {
        this(new RestaurantBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyRestaurantBook newData) {
        versionedRestaurantBook.resetData(newData);
        indicateRestaurantBookChanged();
    }

    @Override
    public ReadOnlyRestaurantBook getRestaurantBook() {
        return versionedRestaurantBook;
    }

    @Override
    public void resetRestaurantBookVersion() {
        versionedRestaurantBook.reset();
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateRestaurantBookChanged() {
        raise(new RestaurantBookChangedEvent(versionedRestaurantBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedRestaurantBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedRestaurantBook.removePerson(target);
        indicateRestaurantBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedRestaurantBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateRestaurantBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedRestaurantBook.updatePerson(target, editedPerson);
        indicateRestaurantBookChanged();
    }

    @Override
    public void removeTag(Tag tag) {
        versionedRestaurantBook.removeTag(tag);
    }

    //=========== Filtered Person List Accessors =============================================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Sales =================================================================================

    @Override
    public boolean hasRecord(SalesRecord record) {
        requireNonNull(record);
        return versionedRestaurantBook.hasRecord(record);
    }

    @Override
    public void deleteRecord(SalesRecord target) {
        versionedRestaurantBook.removeRecord(target);
        indicateRestaurantBookChanged();
    }

    @Override
    public void addRecord(SalesRecord record) {
        versionedRestaurantBook.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        indicateRestaurantBookChanged();
    }

    @Override
    public void updateRecord(SalesRecord target, SalesRecord editedRecord) {
        requireAllNonNull(target, editedRecord);
        versionedRestaurantBook.updateRecord(target, editedRecord);
        indicateRestaurantBookChanged();
    }

    /**
     * Returns the sales report of the specified date.
     *
     * @param date Date of sales report to get
     * @return A SalesReport of the specified date
     */
    @Override
    public SalesReport getSalesReport(Date date) {
        requireNonNull(date);
        return versionedRestaurantBook.getSalesReport(date);
    }

    @Override
    public Map<Date, Double> rankDateBasedOnRevenue() {
        return versionedRestaurantBook.rankDateBasedOnRevenue();
    }

    @Override
    public Map<ItemName, Double> rankItemBasedOnRevenue() {
        return versionedRestaurantBook.rankItemBasedOnRevenue();
    }

    @Override
    public Map<Date, Double> getChronologicalSalesData() {
        return versionedRestaurantBook.getChronologicalSalesData();
    }

    //=========== Filtered Sales Record List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code SalesRecord} backed by the internal list of {@code
     * versionedRestaurantBook}
     */
    @Override
    public ObservableList<SalesRecord> getFilteredRecordList() {
        return FXCollections.unmodifiableObservableList(filteredRecords);
    }

    @Override
    public void updateFilteredRecordList(Predicate<SalesRecord> predicate) {
        requireNonNull(predicate);
        filteredRecords.setPredicate(predicate);
    }

    //=========== Accounts =================================================================================

    //@@author AZhiKai
    @Override
    public void addAccount(Account account) {
        versionedRestaurantBook.addAccount(account);
        indicateRestaurantBookChanged();
    }

    @Override
    public Account getAccount(Account account) {
        return versionedRestaurantBook.getAccount(account);
    }

    @Override
    public boolean hasAccount(Account account) {
        return versionedRestaurantBook.hasAccount(account);
    }

    @Override
    public void removeAccount(Account account) {
        versionedRestaurantBook.removeAccount(account);
        indicateRestaurantBookChanged();
    }

    @Override
    public void updateAccount(Account target, Account editedAccount) {
        versionedRestaurantBook.updateAccount(target, editedAccount);
        indicateRestaurantBookChanged();
    }

    //=========== Filtered Account List Accessors =============================================================

    @Override
    public ObservableList<Account> getFilteredAccountList() {
        return FXCollections.unmodifiableObservableList(filteredAccounts);
    }

    @Override
    public void updateFilteredAccountList(Predicate<Account> predicate) {
        requireNonNull(predicate);
        filteredAccounts.setPredicate(predicate);
    }

    //=========== Ingredients ===============================================================================

    @Override
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return versionedRestaurantBook.hasIngredient(ingredient);
    }

    @Override
    public void deleteIngredient(Ingredient target) {
        versionedRestaurantBook.removeIngredient(target);
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        versionedRestaurantBook.addIngredient(ingredient);
        updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        indicateRestaurantBookChanged();
    }

    @Override
    public Ingredient findIngredient(IngredientName ingredientName) throws IngredientNotFoundException {
        return versionedRestaurantBook.findIngredient(ingredientName);
    }

    @Override
    public void updateIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);

        versionedRestaurantBook.updateIngredient(target, editedIngredient);
        indicateRestaurantBookChanged();
    }

    @Override
    public void stockUpIngredients(Map<IngredientName, Integer> requiredIngredients) {
        requireAllNonNull(requiredIngredients);

        versionedRestaurantBook.stockUpIngredients(requiredIngredients);
        indicateRestaurantBookChanged();
    }

    @Override
    public void consumeIngredients(Map<IngredientName, Integer> requiredIngredients) {
        requireAllNonNull(requiredIngredients);

        versionedRestaurantBook.consumeIngredients(requiredIngredients);
        indicateRestaurantBookChanged();
    }

    //=========== Filtered Ingredient List Accessors =============================================================

    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return FXCollections.unmodifiableObservableList(filteredIngredients);
    }

    @Override
    public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
        requireNonNull(predicate);
        filteredIngredients.setPredicate(predicate);
    }

    //=========== Menu Management ===========================================================================
    //@@author yican95
    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedRestaurantBook.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        versionedRestaurantBook.removeItem(target);
        indicateRestaurantBookChanged();
    }

    @Override
    public void addItem(Item item) {
        versionedRestaurantBook.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        indicateRestaurantBookChanged();
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        versionedRestaurantBook.updateItem(target, editedItem);
        indicateRestaurantBookChanged();
    }

    @Override
    public void removeTagForMenu(Tag tag) {
        versionedRestaurantBook.removeTagForMenu(tag);
    }

    @Override
    public void resetMenuData(ReadOnlyRestaurantBook newData) {
        versionedRestaurantBook.resetMenuData(newData);
        indicateRestaurantBookChanged();
    }

    @Override
    public void sortMenu(SortMethod sortMethod) {
        versionedRestaurantBook.sortMenu(sortMethod);
        indicateRestaurantBookChanged();
    }

    @Override
    public Item findItem(Name name) throws ItemNotFoundException {
        return versionedRestaurantBook.findItem(name);
    }

    @Override
    public Map<IngredientName, Integer> getRequiredIngredients(Item item) {
        return item.getRequiredIngredients();
    }

    //=========== Filtered Item List Accessors ==============================================================

    @Override
    public ObservableList<Item> getFilteredItemList() {
        return FXCollections.unmodifiableObservableList(filteredItems);
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }
    //@@author
    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoRestaurantBook() {
        return versionedRestaurantBook.canUndo();
    }

    @Override
    public boolean canRedoRestaurantBook() {
        return versionedRestaurantBook.canRedo();
    }

    @Override
    public void undoRestaurantBook() {
        versionedRestaurantBook.undo();
        indicateRestaurantBookChanged();
    }

    @Override
    public void redoRestaurantBook() {
        versionedRestaurantBook.redo();
        indicateRestaurantBookChanged();
    }

    @Override
    public void commitRestaurantBook() {
        versionedRestaurantBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedRestaurantBook.equals(other.versionedRestaurantBook)
                && filteredPersons.equals(other.filteredPersons)
                && filteredAccounts.equals(other.filteredAccounts)
                && filteredIngredients.equals(other.filteredIngredients)
                && filteredItems.equals(other.filteredItems)
                && filteredRecords.equals(other.filteredRecords)
                && filteredReservations.equals(other.filteredReservations);
    }

    //=========== Reservations =====================================================================================

    //@@author m4dkip
    @Override
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return versionedRestaurantBook.hasReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation target) {
        versionedRestaurantBook.removeReservation(target);
        indicateRestaurantBookChanged();
    }

    @Override
    public void addReservation(Reservation reservation) {
        versionedRestaurantBook.addReservation(reservation);
        updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        indicateRestaurantBookChanged();
    }

    @Override
    public void updateReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        versionedRestaurantBook.updateReservation(target, editedReservation);
        indicateRestaurantBookChanged();
    }

    @Override
    public void sortReservations() {
        versionedRestaurantBook.sortReservations();
        indicateRestaurantBookChanged();
    }

    @Override
    public void removeTagForReservation(Tag tag) {
        versionedRestaurantBook.removeTagForReservationList(tag);
    }

    //=========== Filtered Reservation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of {@code
     * versionedRestaurantBook}
     */
    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return FXCollections.unmodifiableObservableList(filteredReservations);
    }

    @Override
    public void updateFilteredReservationList(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        filteredReservations.setPredicate(predicate);
    }
}
