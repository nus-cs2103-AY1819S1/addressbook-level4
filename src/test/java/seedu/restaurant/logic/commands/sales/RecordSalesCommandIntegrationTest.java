package seedu.restaurant.logic.commands.sales;

import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_ONE;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;
import seedu.restaurant.testutil.menu.ItemBuilder;
import seedu.restaurant.testutil.sales.RecordBuilder;

//@@author HyperionNKJ
/**
 * Contains integration tests (interaction with the Model) for {@code RecordSalesCommand}.
 */
public class RecordSalesCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    }

    @Test
    public void execute_newRecordButItemNotFoundInMenu_addSuccessfulIngredientListNotUpdated() {
        SalesRecord validRecord = new RecordBuilder(RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_TWO).build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);
        expectedModel.commitRestaurantBook();

        // item with name "VALID_ITEM_NAME_RECORD_TWO" not found in menu. Ingredient list should not be updated.
        assertCommandSuccess(new RecordSalesCommand(validRecord), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord)
                        + "\n" + RecordSalesCommand.MESSAGE_ITEM_NOT_FOUND, expectedModel);
    }

    @Test
    public void execute_newRecordButRequiredIngredientsNotSpecifiedInMenu_addSuccessfulIngredientListNotUpdated() {
        SalesRecord validRecord = new RecordBuilder(RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_TWO).build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);

        Item itemWithoutRequiredIngredients = new ItemBuilder().withName(VALID_ITEM_NAME_RECORD_TWO).build();
        expectedModel.addItem(itemWithoutRequiredIngredients);
        expectedModel.commitRestaurantBook();

        model.addItem(itemWithoutRequiredIngredients);
        // item with name "VALID_ITEM_NAME_RECORD_TWO" found in menu. However, required ingredients not
        // specified. Ingredient list should not be updated.
        assertCommandSuccess(new RecordSalesCommand(validRecord), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord)
                        + "\n" + RecordSalesCommand.MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND, expectedModel);
    }

    @Test
    public void execute_newRecordButSomeIngredientsNotFoundInIngredientList_addSuccessfulIngredientListNotUpdated() {
        SalesRecord validRecord = new RecordBuilder(RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_TWO).build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);

        Map<String, String> requiredIngredients = new HashMap<>();
        requiredIngredients.put("Garlic", "2");
        requiredIngredients.put("Cheese", "3");
        Item itemWithRequiredIngredients = new ItemBuilder().withName(VALID_ITEM_NAME_RECORD_TWO)
                .withRequiredIngredients(requiredIngredients).build();

        expectedModel.addItem(itemWithRequiredIngredients);

        int cheeseNeeded = validRecord.getQuantitySold().getValue() * 3;
        Ingredient enoughCheese = new IngredientBuilder().withName("Cheese").withNumUnits(cheeseNeeded).build();

        model.addItem(itemWithRequiredIngredients);

        // add enough cheese into ingredient list but no garlic
        model.addIngredient(enoughCheese);

        expectedModel.addIngredient(enoughCheese);
        expectedModel.commitRestaurantBook();

        // item with name "VALID_ITEM_NAME_RECORD_TWO" found in menu with required ingredients specified.
        // however, some ingredient was not found in ingredient list. Ingredient list should not be updated.
        assertCommandSuccess(new RecordSalesCommand(validRecord), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord)
                        + "\n" + RecordSalesCommand.MESSAGE_INGREDIENT_NOT_FOUND, expectedModel);
    }

    @Test
    public void execute_newRecordButSomeIngredientsNotEnoughInIngredientList_addSuccessfulIngredientListNotUpdated() {
        SalesRecord validRecord = new RecordBuilder(RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_TWO).build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);

        Map<String, String> requiredIngredients = new HashMap<>();
        requiredIngredients.put("Garlic", "2");
        requiredIngredients.put("Cheese", "3");
        Item itemWithRequiredIngredients = new ItemBuilder().withName(VALID_ITEM_NAME_RECORD_TWO)
                .withRequiredIngredients(requiredIngredients).build();

        expectedModel.addItem(itemWithRequiredIngredients);

        int cheeseNeeded = validRecord.getQuantitySold().getValue() * 3;
        Ingredient enoughCheese = new IngredientBuilder().withName("Cheese").withNumUnits(cheeseNeeded).build();
        int garlicNeeded = validRecord.getQuantitySold().getValue() * 2;
        Ingredient notEnoughGarlic = new IngredientBuilder().withName("Garlic").withNumUnits(garlicNeeded - 1).build();

        model.addItem(itemWithRequiredIngredients);

        // add enough cheese into ingredient list but not enough garlic
        model.addIngredient(enoughCheese);
        model.addIngredient(notEnoughGarlic);

        expectedModel.addIngredient(enoughCheese);
        expectedModel.addIngredient(notEnoughGarlic);
        expectedModel.commitRestaurantBook();

        // item with name "VALID_ITEM_NAME_RECORD_TWO" found in menu with required ingredients specified.
        // however, some ingredient was insufficient. Ingredient list should not be updated.
        assertCommandSuccess(new RecordSalesCommand(validRecord), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord)
                        + "\n" + RecordSalesCommand.MESSAGE_INGREDIENT_NOT_ENOUGH, expectedModel);
    }

    @Test
    public void execute_newRecord_addSuccessfulIngredientListUpdated() {
        SalesRecord validRecord = new RecordBuilder(RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_TWO).build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addRecord(validRecord);

        Map<String, String> requiredIngredients = new HashMap<>();
        requiredIngredients.put("Garlic", "2");
        requiredIngredients.put("Cheese", "3");
        Item itemWithRequiredIngredients = new ItemBuilder().withName(VALID_ITEM_NAME_RECORD_TWO)
                .withRequiredIngredients(requiredIngredients).build();

        expectedModel.addItem(itemWithRequiredIngredients);

        int cheeseNeeded = validRecord.getQuantitySold().getValue() * 3;
        Ingredient enoughCheese = new IngredientBuilder().withName("Cheese").withNumUnits(cheeseNeeded + 5).build();
        int garlicNeeded = validRecord.getQuantitySold().getValue() * 2;
        Ingredient enoughGarlic = new IngredientBuilder().withName("Garlic").withNumUnits(garlicNeeded + 6).build();

        model.addItem(itemWithRequiredIngredients);

        // add enough cheese and garlic into ingredient list
        model.addIngredient(enoughCheese);
        model.addIngredient(enoughGarlic);

        Ingredient leftoverCheese = new IngredientBuilder().withName("Cheese").withNumUnits(5).build();
        Ingredient leftoverGarlic = new IngredientBuilder().withName("Garlic").withNumUnits(6).build();
        expectedModel.addIngredient(leftoverCheese);
        expectedModel.addIngredient(leftoverGarlic);
        expectedModel.commitRestaurantBook();

        // item with name "VALID_ITEM_NAME_RECORD_TWO" found in menu with required ingredients specified.
        // All ingredients are present and sufficient in ingredient list. Ingredient list updated.
        assertCommandSuccess(new RecordSalesCommand(validRecord), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_RECORD_SALES_SUCCESS, validRecord)
                        + "\n" + RecordSalesCommand.MESSAGE_INGREDIENT_UPDATE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_duplicateRecord_throwsCommandException() {
        SalesRecord recordInList = model.getRestaurantBook().getRecordList().get(0);
        assertCommandFailure(new RecordSalesCommand(recordInList), model, commandHistory,
                String.format(RecordSalesCommand.MESSAGE_DUPLICATE_SALES_RECORD, recordInList.getName()));
    }
}
