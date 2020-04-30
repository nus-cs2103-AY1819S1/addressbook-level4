package seedu.restaurant.logic.commands.reservation;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ReadOnlyRestaurantBook;
import seedu.restaurant.model.RestaurantBook;
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
import seedu.restaurant.testutil.reservation.ReservationBuilder;

//@@author m4dkip
public class AddReservationCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReservationCommand(null);
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReservationAdded modelStub = new ModelStubAcceptingReservationAdded();
        Reservation validReservation = new ReservationBuilder().build();

        CommandResult commandResult = new AddReservationCommand(validReservation).execute(modelStub, commandHistory);

        assertEquals(String.format(AddReservationCommand.MESSAGE_SUCCESS, validReservation),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validReservation), modelStub.getReservationsAdded());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateReservation_throwsCommandException() throws Exception {
        Reservation validReservation = new ReservationBuilder().build();
        AddReservationCommand addReservationCommand = new AddReservationCommand(validReservation);
        ModelStub modelStub = new ModelStubWithReservation(validReservation);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReservationCommand.MESSAGE_DUPLICATE_RESERVATION);
        addReservationCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Reservation andrew = new ReservationBuilder().withName("Andrew").build();
        Reservation billy = new ReservationBuilder().withName("Billy").build();
        AddReservationCommand addAndrewCommand = new AddReservationCommand(andrew);
        AddReservationCommand addBillyCommand = new AddReservationCommand(billy);

        // same object -> returns true
        assertTrue(addAndrewCommand.equals(addAndrewCommand));

        // same values -> returns true
        AddReservationCommand addAndrewCommandCopy = new AddReservationCommand(andrew);
        assertTrue(addAndrewCommand.equals(addAndrewCommandCopy));

        // different types -> returns false
        assertFalse(addAndrewCommand.equals(1));

        // null -> returns false
        assertFalse(addAndrewCommand.equals(null));

        // different item -> returns false
        assertFalse(addAndrewCommand.equals(addBillyCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyRestaurantBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetRestaurantBookVersion() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAccount(Account account) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Account getAccount(Account account) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAccount(Account account) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAccount(Account account) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void updateAccount(Account target, Account editedAccount) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Account> getFilteredAccountList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAccountList(Predicate<Account> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // Menu Management
        @Override
        public void addItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateItem(Item target, Item editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortMenu(SortMethod sortMethod) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Item findItem(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<IngredientName, Integer> getRequiredIngredients(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTagForMenu(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetMenuData(ReadOnlyRestaurantBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Item> getFilteredItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredItemList(Predicate<Item> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // Ingredient Management
        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Ingredient findIngredient(IngredientName ingredientName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteIngredient(Ingredient ingredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateIngredient(Ingredient target, Ingredient editedIngredient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void stockUpIngredients(Map<IngredientName, Integer> recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void consumeIngredients(Map<IngredientName, Integer> recipe) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Ingredient> getFilteredIngredientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // Reservation Management
        @Override
        public boolean hasReservation(Reservation reservation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReservation(Reservation target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReservation(Reservation reservation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateReservation(Reservation target, Reservation editedReservation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortReservations() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTagForReservation(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reservation> getFilteredReservationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredReservationList(Predicate<Reservation> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecord(SalesRecord record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRecord(SalesRecord record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecord(SalesRecord target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRecord(SalesRecord target, SalesRecord editedRecord) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SalesReport getSalesReport(Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<Date, Double> rankDateBasedOnRevenue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<ItemName, Double> rankItemBasedOnRevenue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<Date, Double> getChronologicalSalesData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<SalesRecord> getFilteredRecordList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecordList(Predicate<SalesRecord> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single reservation.
     */
    private class ModelStubWithReservation extends ModelStub {

        private final Reservation reservation;

        ModelStubWithReservation(Reservation reservation) {
            requireNonNull(reservation);
            this.reservation = reservation;
        }

        @Override
        public boolean hasReservation(Reservation reservation) {
            requireNonNull(reservation);
            return this.reservation.isSameReservation(reservation);
        }
    }

    /**
     * A Model stub that always accept the reservation being added.
     */
    private class ModelStubAcceptingReservationAdded extends ModelStub {

        private final ArrayList<Reservation> reservationsAdded = new ArrayList<>();

        @Override
        public boolean hasReservation(Reservation reservation) {
            requireNonNull(reservation);
            return reservationsAdded.stream().anyMatch(reservation::isSameReservation);
        }

        @Override
        public void addReservation(Reservation reservation) {
            requireNonNull(reservation);
            reservationsAdded.add(reservation);
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }

        public ArrayList<Reservation> getReservationsAdded() {
            return reservationsAdded;
        }
    }

}
