package seedu.restaurant.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_RECORDS;
import static seedu.restaurant.testutil.TypicalPersons.ALICE;
import static seedu.restaurant.testutil.TypicalPersons.AMY;
import static seedu.restaurant.testutil.TypicalPersons.BENSON;
import static seedu.restaurant.testutil.TypicalPersons.BOB;
import static seedu.restaurant.testutil.TypicalPersons.DYLAN;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ADMIN;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ONE;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_TWO;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.AVOCADO;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.BROCCOLI;
import static seedu.restaurant.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.restaurant.testutil.menu.TypicalItems.BURGER;
import static seedu.restaurant.testutil.menu.TypicalItems.CHEESE_BURGER;
import static seedu.restaurant.testutil.menu.TypicalItems.FRIES;
import static seedu.restaurant.testutil.menu.TypicalItems.ITEM_DEFAULT;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.RECORD_DEFAULT;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.RECORD_ONE;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.RECORD_TWO;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.exceptions.AccountNotFoundException;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.NumUnits;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotEnoughException;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.exceptions.ItemNotFoundException;
import seedu.restaurant.model.person.NameContainsKeywordsPredicate;
import seedu.restaurant.model.person.Person;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.model.sales.exceptions.SalesRecordNotFoundException;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.PersonBuilder;
import seedu.restaurant.testutil.RestaurantBookBuilder;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;
import seedu.restaurant.testutil.menu.ItemBuilder;
import seedu.restaurant.testutil.salesrecords.RecordBuilder;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();
    private RestaurantBook restaurantBookWithPersons = null;

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInRestaurantBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInRestaurantBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRecordList().remove(0);
    }

    @Test
    public void getFilteredAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAccountList().remove(0);
    }

    //@@author yican95
    @Test
    public void getFilteredItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredItemList().remove(0);
    }

    @Test
    public void removeTag_noSuchTag_restaurantBookUnmodified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager unmodifiedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);
        unmodifiedModelManager.removeTag(new Tag(VALID_TAG_TEST));

        ModelManager expectedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);

        assertEquals(unmodifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTag_fromAllPersons_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withPerson(AMY).withPerson(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);
        modifiedModelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        ModelManager expectedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);
        // Cannot init a new RestaurantBook due to difference in restaurantBookStateList
        expectedModelManager.updatePerson(AMY, amyWithoutTags);
        expectedModelManager.updatePerson(BOB, bobWithoutFriendTag);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTag_fromOnePerson_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withPerson(AMY).withPerson(DYLAN).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);
        modifiedModelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        ModelManager expectedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);
        expectedModelManager.updatePerson(AMY, amyWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRecord(null);
    }
    @Test
    public void hasRecord_recordNotInSalesBook_returnsFalse() {
        assertFalse(modelManager.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void hasRecord_recordInSalesBook_returnsTrue() {
        modelManager.addRecord(RECORD_DEFAULT);
        assertTrue(modelManager.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getRestaurantBook().getRecordList().remove(0);
    }
    @Test
    public void deleteRecord_recordNotInSalesBook_throwsRecordsNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        modelManager.deleteRecord(RECORD_DEFAULT);
    }
    @Test
    public void deleteRecord_recordInSalesBook_returnTrue() {
        modelManager.addRecord(RECORD_DEFAULT);
        assertTrue(modelManager.hasRecord(RECORD_DEFAULT));
        modelManager.deleteRecord(RECORD_DEFAULT);
        assertFalse(modelManager.hasRecord(RECORD_DEFAULT));
    }
    @Test
    public void updateRecord_recordNotInSalesBook_throwsRecordNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        modelManager.updateRecord(RECORD_DEFAULT, RECORD_ONE);
    }
    @Test
    public void updateRecord_recordInSalesBook_returnTrue() {
        modelManager.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_ONE).build();
        modelManager.updateRecord(RECORD_DEFAULT, record);
        assertFalse(modelManager.hasRecord(RECORD_DEFAULT));
        assertTrue(modelManager.hasRecord(record));
    }
    @Test
    public void getSalesReport_nullDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.getSalesReport(null);
    }

    //@@author AZhiKai
    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAccount(null);
    }

    @Test
    public void hasAccount_accountNotInAccountRecord_returnsFalse() {
        assertFalse(modelManager.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountInAccountRecord_returnsTrue() {
        modelManager.addAccount(DEMO_ADMIN);
        assertTrue(modelManager.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getRestaurantBook().getAccountList().remove(0);
    }

    @Test
    public void removeAccount_accountNotInAccountRecord_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        modelManager.removeAccount(DEMO_ADMIN);
    }

    @Test
    public void removeAccount_accountInAccountRecord_returnTrue() {
        modelManager.addAccount(DEMO_ADMIN);
        assertTrue(modelManager.hasAccount(DEMO_ADMIN));

        modelManager.removeAccount(DEMO_ADMIN);
        assertFalse(modelManager.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void updateAccount_accountNotInAccountRecord_throwsAccountNotFoundException() {
        thrown.expect(AccountNotFoundException.class);
        modelManager.updateAccount(DEMO_ADMIN, DEMO_ONE);
    }

    @Test
    public void updateAccount_accountInAccountRecord_returnTrue() {
        modelManager.addAccount(DEMO_ADMIN);
        Account account = new AccountBuilder(DEMO_ONE).build();

        modelManager.updateAccount(DEMO_ADMIN, account);
        assertFalse(modelManager.hasAccount(DEMO_ADMIN));
        assertTrue(modelManager.hasAccount(account));
    }

    @Test
    public void hasIngredient_nullIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasIngredient(null);
    }

    @Test
    public void hasIngredient_ingredientNotIngredientList_returnsFalse() {
        assertFalse(modelManager.hasIngredient(AVOCADO));
    }

    @Test
    public void hasIngredient_ingredientInIngredientList_returnsTrue() {
        modelManager.addIngredient(AVOCADO);
        assertTrue(modelManager.hasIngredient(AVOCADO));
    }

    @Test
    public void deleteIngredient_ingredientNotInIngredientList_throwsItemNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        modelManager.deleteIngredient(BROCCOLI);
    }
    @Test
    public void deleteIngredient_ingredientInIngredientList_returnTrue() {
        modelManager.addIngredient(BROCCOLI);
        assertTrue(modelManager.hasIngredient(BROCCOLI));
        modelManager.deleteIngredient(BROCCOLI);
        assertFalse(modelManager.hasIngredient(BROCCOLI));
    }
    @Test
    public void updateIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        modelManager.updateIngredient(BROCCOLI, AVOCADO);
    }
    @Test
    public void updateIngredient_ingredientInIngredientList_returnTrue() {
        modelManager.addIngredient(BROCCOLI);
        Ingredient ingredient = new IngredientBuilder(AVOCADO).build();
        modelManager.updateIngredient(BROCCOLI, AVOCADO);
        assertFalse(modelManager.hasIngredient(BROCCOLI));
        assertTrue(modelManager.hasIngredient(ingredient));
    }

    @Test
    public void findIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        modelManager.findIngredient(BROCCOLI.getName());
    }

    @Test
    public void findIngredient_ingredientInIngredientList_assertEquals() {
        modelManager.addIngredient(AVOCADO);
        Ingredient ingredient = modelManager.findIngredient(AVOCADO.getName());
        assertEquals(AVOCADO, ingredient);
    }

    @Test
    public void stockUpIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(BROCCOLI.getName(), 12);
        thrown.expect(IngredientNotFoundException.class);
        modelManager.stockUpIngredients(requiredIngredients);
    }

    @Test
    public void stockUpIngredient_ingredientInIngredientList_assertEquals() {
        Ingredient ingredient = new IngredientBuilder(BROCCOLI).withNumUnits(20).build();
        modelManager.addIngredient(ingredient);
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(BROCCOLI.getName(), 12);
        modelManager.stockUpIngredients(requiredIngredients);
        Ingredient stockedUpIngredient = modelManager.findIngredient(BROCCOLI.getName());
        NumUnits updatedNumUnits = stockedUpIngredient.getNumUnits();
        assertEquals(new NumUnits(32), updatedNumUnits);
    }

    @Test
    public void consumeIngredient_ingredientNotInIngredientList_throwsIngredientNotFoundException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(BROCCOLI.getName(), 12);
        thrown.expect(IngredientNotFoundException.class);
        modelManager.consumeIngredients(requiredIngredients);
    }

    @Test
    public void consumeIngredient_ingredientNotEnough_throwsIngredientNotEnoughException() {
        Ingredient ingredient = new IngredientBuilder(BROCCOLI).withNumUnits(2).build();
        modelManager.addIngredient(ingredient);
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(BROCCOLI.getName(), 12);
        thrown.expect(IngredientNotEnoughException.class);
        modelManager.consumeIngredients(requiredIngredients);
    }

    @Test
    public void consumeIngredient_ingredientInIngredientList_assertEquals() {
        Ingredient ingredient = new IngredientBuilder(BROCCOLI).withNumUnits(20).build();
        modelManager.addIngredient(ingredient);
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(BROCCOLI.getName(), 2);
        modelManager.consumeIngredients(requiredIngredients);
        Ingredient consumedIngredient = modelManager.findIngredient(BROCCOLI.getName());
        NumUnits updatedNumUnits = consumedIngredient.getNumUnits();
        assertEquals(new NumUnits(18), updatedNumUnits);
    }

    //@@author yican95
    // Menu Management
    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInRestaurantBook_returnsFalse() {
        assertFalse(modelManager.hasItem(APPLE_JUICE));
    }

    @Test
    public void hasItem_itemInRestaurantBook_returnsTrue() {
        modelManager.addItem(APPLE_JUICE);
        assertTrue(modelManager.hasItem(APPLE_JUICE));
    }

    @Test
    public void deleteItem_itemNotInMenu_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        modelManager.deleteItem(ITEM_DEFAULT);
    }
    @Test
    public void deleteItem_itemInMenu_returnTrue() {
        modelManager.addItem(ITEM_DEFAULT);
        assertTrue(modelManager.hasItem(ITEM_DEFAULT));
        modelManager.deleteItem(ITEM_DEFAULT);
        assertFalse(modelManager.hasItem(ITEM_DEFAULT));
    }
    @Test
    public void updateItem_itemNotInMenu_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        modelManager.updateItem(ITEM_DEFAULT, APPLE_JUICE);
    }
    @Test
    public void updateItem_itemInMenu_returnTrue() {
        modelManager.addItem(ITEM_DEFAULT);
        Item item = new ItemBuilder(APPLE_JUICE).build();
        modelManager.updateItem(ITEM_DEFAULT, item);
        assertFalse(modelManager.hasItem(ITEM_DEFAULT));
        assertTrue(modelManager.hasItem(item));
    }

    @Test
    public void removeTagForMenu_noSuchTag_restaurantBookUnmodified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(APPLE_JUICE).withItem(BURGER).build();

        ModelManager unmodifiedModelManager = new ModelManager(restaurantBookWithPersons, new UserPrefs());
        unmodifiedModelManager.removeTagForMenu(new Tag(VALID_TAG_TEST));

        ModelManager expectedModelManager = new ModelManager(restaurantBookWithPersons, new UserPrefs());

        assertEquals(unmodifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTagForMenu_fromAllItems_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(CHEESE_BURGER).withItem(FRIES).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modifiedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);
        modifiedModelManager.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item cheeseWithoutCheeseTags = new ItemBuilder(CHEESE_BURGER).withTags(VALID_ITEM_TAG_BURGER).build();
        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        ModelManager expectedModelManager = new ModelManager(restaurantBookWithPersons, userPrefs);
        // Cannot init a new RestaurantBook due to difference in restaurantBookStateList
        expectedModelManager.updateItem(CHEESE_BURGER, cheeseWithoutCheeseTags);
        expectedModelManager.updateItem(FRIES, friesWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    @Test
    public void removeTagForMenu_fromOneItem_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(FRIES).withItem(BURGER).build();

        ModelManager modifiedModelManager = new ModelManager(restaurantBookWithPersons, new UserPrefs());
        modifiedModelManager.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        ModelManager expectedModelManager = new ModelManager(restaurantBookWithPersons, new UserPrefs());
        expectedModelManager.updateItem(FRIES, friesWithoutTags);

        assertEquals(modifiedModelManager, expectedModelManager);
    }

    //@@author
    @Test
    public void equals() {
        RestaurantBook restaurantBook = new RestaurantBookBuilder()
                .withPerson(ALICE)
                .withPerson(BENSON)
                .withRecord(RECORD_ONE)
                .withRecord(RECORD_TWO)
                .withAccount(DEMO_ONE)
                .withAccount(DEMO_TWO)
                .build();
        RestaurantBook differentRestaurantBook = new RestaurantBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(restaurantBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(restaurantBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different restaurantBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRestaurantBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().toString().split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(restaurantBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        modelManager.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRestaurantBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(restaurantBook, differentUserPrefs)));
    }
}
