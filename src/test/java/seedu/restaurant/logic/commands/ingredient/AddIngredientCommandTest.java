package seedu.restaurant.logic.commands.ingredient;

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
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

//@@author rebstan97
public class AddIngredientCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddIngredientCommand(null);
    }

    @Test
    public void execute_ingredientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIngredientAdded modelStub = new ModelStubAcceptingIngredientAdded();
        Ingredient validIngredient = new IngredientBuilder().build();

        CommandResult commandResult = new AddIngredientCommand(validIngredient).execute(modelStub, commandHistory);

        assertEquals(String.format(AddIngredientCommand.MESSAGE_SUCCESS, validIngredient),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validIngredient), modelStub.ingredientsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() throws Exception {
        Ingredient validIngredient = new IngredientBuilder().build();
        AddIngredientCommand addIngredientCommand = new AddIngredientCommand(validIngredient);
        ModelStub modelStub = new ModelStubWithIngredient(validIngredient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddIngredientCommand.MESSAGE_DUPLICATE_INGREDIENT);
        addIngredientCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Ingredient apple = new IngredientBuilder().withName("Apple").build();
        Ingredient broccoli = new IngredientBuilder().withName("Broccoli").build();
        AddIngredientCommand addAppleCommand = new AddIngredientCommand(apple);
        AddIngredientCommand addBroccoliCommand = new AddIngredientCommand(broccoli);

        // same object -> returns true
        assertTrue(addAppleCommand.equals(addAppleCommand));

        // same values -> returns true
        AddIngredientCommand addAppleCommandCopy = new AddIngredientCommand(apple);
        assertTrue(addAppleCommand.equals(addAppleCommandCopy));

        // different types -> returns false
        assertFalse(addAppleCommand.equals(1));

        // null -> returns false
        assertFalse(addAppleCommand.equals(null));

        // different item -> returns false
        assertFalse(addAppleCommand.equals(addBroccoliCommand));
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

        @Override
        public void addItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateItem(Item target, Item editedRecord) {
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
        public ObservableList<Item> getFilteredItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredItemList(Predicate<Item> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetMenuData(ReadOnlyRestaurantBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortMenu(SortMethod sortMethod) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the ingredient being added.
     */
    private class ModelStubAcceptingIngredientAdded extends ModelStub {

        private final ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return ingredientsAdded.stream().anyMatch(ingredient::isSameIngredient);
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            ingredientsAdded.add(ingredient);
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code AddIngredientCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }
    }

    /**
     * A Model stub that contains an ingredient.
     */
    private class ModelStubWithIngredient extends ModelStub {

        private final Ingredient ingredient;

        ModelStubWithIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            this.ingredient = ingredient;
        }

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return this.ingredient.isSameIngredient(ingredient);
        }
    }

}
