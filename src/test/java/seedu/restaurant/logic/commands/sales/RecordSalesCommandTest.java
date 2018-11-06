package seedu.restaurant.logic.commands.sales;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import seedu.restaurant.model.ingredient.NumUnits;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotEnoughException;
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
import seedu.restaurant.testutil.ingredient.IngredientBuilder;
import seedu.restaurant.testutil.menu.ItemBuilder;
import seedu.restaurant.testutil.sales.RecordBuilder;

public class RecordSalesCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ModelStubAcceptingRecordAdded modelStub = new ModelStubAcceptingRecordAdded();
    private final SalesRecord validRecord = new RecordBuilder().build();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RecordSalesCommand(null);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() throws Exception {
        SalesRecord validRecord = new RecordBuilder().build();
        RecordSalesCommand recordSalesCommand = new RecordSalesCommand(validRecord);
        ModelStub modelStub = new ModelStubWithRecord(validRecord);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(RecordSalesCommand.MESSAGE_DUPLICATE_SALES_RECORD, validRecord.getName()));
        recordSalesCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_recordAcceptedButItemNotFoundInMenu_addSuccessfulIngredientListNotUpdated() throws Exception {
        CommandResult commandResult = new RecordSalesCommand(validRecord).execute(modelStub, commandHistory);
        // empty menu -> item not found
        assertEquals(String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord) + "\n"
                + RecordSalesCommand.MESSAGE_ITEM_NOT_FOUND, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_recordAcceptedButRequiredIngredientsNotSpecifiedInMenu_addSuccessfulIngredientListNotUpdated()
            throws Exception {
        // add item into menu but did not specify required ingredients
        Item itemWithoutRequiredIngredients = new ItemBuilder().withName(validRecord.getName().toString()).build();
        modelStub.addItem(itemWithoutRequiredIngredients);

        // required ingredients of items not found
        CommandResult commandResult = new RecordSalesCommand(validRecord).execute(modelStub, commandHistory);
        assertEquals(String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord) + "\n"
                + RecordSalesCommand.MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_recordAcceptedButSomeIngredientsNotFoundInIngredientList_addSuccessfulIngredientListNotUpdated()
            throws Exception {
        Map<String, String> requiredIngredients = new HashMap<>();
        requiredIngredients.put("Carrot", "1");
        requiredIngredients.put("Onion", "2");

        // add item into menu and specify required ingredients
        Item itemWithRequiredIngredients = new ItemBuilder().withName(validRecord.getName().toString())
                .withRequiredIngredients(requiredIngredients).build();
        modelStub.addItem(itemWithRequiredIngredients);

        // add enough onion into ingredient list but no carrot
        int onionNeeded = validRecord.getQuantitySold().getValue() * 2;
        Ingredient enoughOnion = new IngredientBuilder().withName("Onion").withNumUnits(onionNeeded).build();
        modelStub.addIngredient(enoughOnion);

        // carrot not found in ingredient list -> ingredient list should not be updated -> quantity of onion remains
        // unchanged
        CommandResult commandResult = new RecordSalesCommand(validRecord).execute(modelStub, commandHistory);
        assertEquals(String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord) + "\n"
                + RecordSalesCommand.MESSAGE_INGREDIENT_NOT_FOUND, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        assertEquals(onionNeeded, modelStub.ingredientsAdded.get(0).getNumUnits().getNumberOfUnits());
    }

    @Test
    public void execute_recordAcceptedButSomeIngredientNotEnoughInIngredientList_addSuccessfulIngredientListNotUpdated()
            throws Exception {
        Map<String, String> requiredIngredients = new HashMap<>();
        requiredIngredients.put("Carrot", "1");
        requiredIngredients.put("Onion", "2");

        // add item into menu and specify required ingredients
        Item itemWithRequiredIngredients = new ItemBuilder().withName(validRecord.getName().toString())
                .withRequiredIngredients(requiredIngredients).build();
        modelStub.addItem(itemWithRequiredIngredients);

        // add enough onion into ingredient list but not enough carrot
        int onionNeeded = validRecord.getQuantitySold().getValue() * 2;
        Ingredient enoughOnion = new IngredientBuilder().withName("Onion").withNumUnits(onionNeeded).build();
        Ingredient notEnoughCarrot = new IngredientBuilder().withName("Carrot").withNumUnits(3).build();
        modelStub.addIngredient(enoughOnion);
        modelStub.addIngredient(notEnoughCarrot);

        // carrot not enough -> ingredient list should not be updated -> quantity of onion and carrots remains unchanged
        CommandResult commandResult = new RecordSalesCommand(validRecord).execute(modelStub, commandHistory);
        assertEquals(String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord) + "\n"
                + RecordSalesCommand.MESSAGE_INGREDIENT_NOT_ENOUGH, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        assertEquals(onionNeeded, modelStub.ingredientsAdded.get(0).getNumUnits().getNumberOfUnits());
        assertEquals(3, modelStub.ingredientsAdded.get(1).getNumUnits().getNumberOfUnits());
    }

    @Test
    public void execute_recordAccepted_addSuccessfulIngredientListUpdated() throws Exception {
        Map<String, String> requiredIngredients = new HashMap<>();
        requiredIngredients.put("Carrot", "3");
        requiredIngredients.put("Onion", "2");

        // add item into menu and specify required ingredients
        Item itemWithRequiredIngredients = new ItemBuilder().withName(validRecord.getName().toString())
                .withRequiredIngredients(requiredIngredients).build();
        modelStub.addItem(itemWithRequiredIngredients);

        // add enough onion and carrot into ingredient list
        int onionNeeded = validRecord.getQuantitySold().getValue() * 2;
        Ingredient enoughOnion = new IngredientBuilder().withName("Onion").withNumUnits(onionNeeded + 10).build();
        int carrotNeeded = validRecord.getQuantitySold().getValue() * 3;
        Ingredient enoughCarrot = new IngredientBuilder().withName("Carrot").withNumUnits(carrotNeeded + 5).build();
        modelStub.addIngredient(enoughOnion);
        modelStub.addIngredient(enoughCarrot);

        // item exists in menu with required ingredients specified. Required ingredients all sufficient in ingredient
        // list -> ingredient list updated -> quantity of onion and carrot decreased
        CommandResult commandResult = new RecordSalesCommand(validRecord).execute(modelStub, commandHistory);
        assertEquals(String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord) + "\n"
                + RecordSalesCommand.MESSAGE_INGREDIENT_UPDATE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecord), modelStub.recordsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        assertEquals(10, modelStub.ingredientsAdded.get(0).getNumUnits().getNumberOfUnits());
        assertEquals(5, modelStub.ingredientsAdded.get(1).getNumUnits().getNumberOfUnits());
    }

    @Test
    public void equals() {
        SalesRecord record1 = new RecordBuilder().withDate("28-02-2018").withName("Sweet Potato").build();
        SalesRecord record2 = new RecordBuilder().withDate("11-08-2017").withName("Apple Juice").build();

        RecordSalesCommand recordRecord1Command = new RecordSalesCommand(record1);
        RecordSalesCommand recordRecord2Command = new RecordSalesCommand(record2);

        // same object -> returns true
        assertTrue(recordRecord1Command.equals(recordRecord1Command));

        // same values -> returns true
        RecordSalesCommand recordRecord1CommandCopy = new RecordSalesCommand(record1);
        assertTrue(recordRecord1Command.equals(recordRecord1CommandCopy));

        // different types -> returns false
        assertFalse(recordRecord1Command.equals(1));

        // null -> returns false
        assertFalse(recordRecord1Command.equals(null));

        // different person -> returns false
        assertFalse(recordRecord1Command.equals(recordRecord2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
        public void removeTagForMenu(Tag tag) {
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

        @Override
        public Item findItem(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Map<IngredientName, Integer> getRequiredIngredients(Item item) {
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
    }

    /**
     * A Model stub that contains a single record.
     */
    private class ModelStubWithRecord extends ModelStub {

        private final SalesRecord record;

        ModelStubWithRecord(SalesRecord record) {
            requireNonNull(record);
            this.record = record;
        }

        @Override
        public boolean hasRecord(SalesRecord record) {
            requireNonNull(record);
            return this.record.isSameRecord(record);
        }
    }

    /**
     * A Model stub that always accept the record being added.
     */
    private class ModelStubAcceptingRecordAdded extends ModelStub {

        private final ArrayList<SalesRecord> recordsAdded = new ArrayList<>();
        private final ArrayList<Item> itemsAdded = new ArrayList<>();
        private final ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();

        @Override
        public boolean hasRecord(SalesRecord record) {
            requireNonNull(record);
            return recordsAdded.stream().anyMatch(record::isSameRecord);
        }

        @Override
        public void addRecord(SalesRecord record) {
            requireNonNull(record);
            recordsAdded.add(record);
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code RecordSalesCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }

        @Override
        public void addItem(Item item) {
            requireNonNull(item);
            itemsAdded.add(item);
        }

        @Override
        public void addIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            ingredientsAdded.add(ingredient);
        }

        @Override
        public Item findItem(Name name) throws ItemNotFoundException {
            requireNonNull(name);
            for (Item item : itemsAdded) {
                if (item.getName().toString().equalsIgnoreCase(name.toString())) {
                    return item;
                }
            }
            throw new ItemNotFoundException();
        }

        @Override
        public Ingredient findIngredient(IngredientName name) throws IngredientNotFoundException {
            requireNonNull(name);
            for (Ingredient ingredient : ingredientsAdded) {
                if (ingredient.getName().toString().equalsIgnoreCase(name.toString())) {
                    return ingredient;
                }
            }
            throw new IngredientNotFoundException();
        }

        @Override
        public Map<IngredientName, Integer> getRequiredIngredients(Item item) {
            return item.getRequiredIngredients();
        }

        /**
         * Stocks up the ingredients in this model stub according to the Map given
         * @param consumedIngredients
         */
        private void stockUp(Map<IngredientName, Integer> consumedIngredients) {
            for (Map.Entry<IngredientName, Integer> entry : consumedIngredients.entrySet()) {
                IngredientName name = entry.getKey();
                Integer unitsConsumed = entry.getValue();
                Ingredient ingredient = findIngredient(name);
                int index = ingredientsAdded.indexOf(ingredient);
                NumUnits updatedNumUnits = ingredient.getNumUnits().increase(unitsConsumed);
                Ingredient stockedIngredient = new Ingredient(ingredient.getName(), ingredient.getUnit(),
                        ingredient.getPrice(), ingredient.getMinimum(), updatedNumUnits);
                ingredientsAdded.set(index, stockedIngredient);
            }
        }

        @Override
        public void consumeIngredients(Map<IngredientName, Integer> requiredIngredients) throws
                IngredientNotFoundException, IngredientNotEnoughException {
            requireNonNull(requiredIngredients);
            Map<IngredientName, Integer> consumedIngredients = new HashMap<>();
            for (Map.Entry<IngredientName, Integer> ingredientPair : requiredIngredients.entrySet()) {
                IngredientName name = ingredientPair.getKey();
                Integer unitsToConsume = ingredientPair.getValue();
                Ingredient ingredient;
                try {
                    ingredient = findIngredient(name);
                } catch (IngredientNotFoundException e) {
                    stockUp(consumedIngredients);
                    throw new IngredientNotFoundException();
                }
                if (ingredient.getNumUnits().getNumberOfUnits() < unitsToConsume) {
                    stockUp(consumedIngredients);
                    throw new IngredientNotEnoughException();
                }
                consumedIngredients.put(name, unitsToConsume);
                int index = ingredientsAdded.indexOf(ingredient);
                NumUnits updatedNumUnits = ingredient.getNumUnits().decrease(unitsToConsume);
                Ingredient consumedIngredient = new Ingredient(ingredient.getName(), ingredient.getUnit(),
                        ingredient.getPrice(), ingredient.getMinimum(), updatedNumUnits);
                ingredientsAdded.set(index, consumedIngredient);
            }
        }
    }
}
