package seedu.restaurant.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ID;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_ITEM_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PASSWORD;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PAX;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PERCENT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_QUANTITY_SOLD;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TIME;
import static seedu.restaurant.testutil.menu.TypicalItems.FRIES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.account.ChangePasswordCommand;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand;
import seedu.restaurant.logic.commands.menu.EditItemCommand;
import seedu.restaurant.logic.commands.reservation.EditReservationCommand;
import seedu.restaurant.logic.commands.sales.EditSalesCommand;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientNameContainsKeywordsPredicate;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.NameContainsKeywordsPredicate;
import seedu.restaurant.storage.XmlAdaptedTag;
import seedu.restaurant.testutil.account.EditAccountDescriptorBuilder;
import seedu.restaurant.testutil.ingredient.EditIngredientDescriptorBuilder;
import seedu.restaurant.testutil.menu.EditItemDescriptorBuilder;
import seedu.restaurant.testutil.reservation.EditReservationDescriptorBuilder;
import seedu.restaurant.testutil.sales.EditRecordDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_TEST = "test";
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    /**
     * For sales records
     */
    public static final String VALID_DATE_RECORD_ONE = "28-02-2018";
    public static final String VALID_DATE_RECORD_TWO = "11-11-2011";
    public static final String VALID_DATE_RECORD_THREE = "25-12-2017";
    public static final String VALID_ITEM_NAME_RECORD_ONE = "Cheese Pizza";
    public static final String VALID_ITEM_NAME_RECORD_TWO = "Pasta";
    public static final String VALID_ITEM_NAME_RECORD_THREE = "Orange Juice";
    public static final String VALID_QUANTITY_SOLD_RECORD_ONE = "100";
    public static final String VALID_QUANTITY_SOLD_RECORD_TWO = "87";
    public static final String VALID_QUANTITY_SOLD_RECORD_THREE = "202";
    public static final String VALID_PRICE_RECORD_ONE = "18.50";
    public static final String VALID_PRICE_RECORD_TWO = "7.99";
    public static final String VALID_PRICE_RECORD_THREE = "2";

    public static final String PREFIX_WITH_VALID_DATE = " " + PREFIX_DATE + VALID_DATE_RECORD_ONE;
    public static final String PREFIX_WITH_VALID_ITEM_NAME = " " + PREFIX_ITEM_NAME + VALID_ITEM_NAME_RECORD_ONE;
    public static final String PREFIX_WITH_VALID_QUANTITY_SOLD =
            " " + PREFIX_QUANTITY_SOLD + VALID_QUANTITY_SOLD_RECORD_ONE;
    public static final String PREFIX_WITH_VALID_PRICE = " " + PREFIX_ITEM_PRICE + VALID_PRICE_RECORD_ONE;
    public static final String PREFIX_WITH_VALID_DATE_TWO = " " + PREFIX_DATE + VALID_DATE_RECORD_TWO;
    public static final String PREFIX_WITH_VALID_ITEM_NAME_TWO = " " + PREFIX_ITEM_NAME + VALID_ITEM_NAME_RECORD_TWO;
    public static final String PREFIX_WITH_VALID_QUANTITY_SOLD_TWO =
            " " + PREFIX_QUANTITY_SOLD + VALID_QUANTITY_SOLD_RECORD_TWO;
    public static final String PREFIX_WITH_VALID_PRICE_TWO = " " + PREFIX_ITEM_PRICE + VALID_PRICE_RECORD_TWO;
    public static final String PREFIX_WITH_INVALID_DATE = " " + PREFIX_DATE + "31-02-2018"; // no such date
    public static final String PREFIX_WITH_INVALID_ITEM_NAME = " " + PREFIX_ITEM_NAME + "Fried Rice!"; // symbols not
    // allowed
    public static final String PREFIX_WITH_INVALID_QUANTITY_SOLD = " " + PREFIX_QUANTITY_SOLD + "3.5"; // positive
    // integer only
    public static final String PREFIX_WITH_INVALID_PRICE = " " + PREFIX_ITEM_PRICE + "-2"; // negative price not allowed

    //@@author AZhiKai
    /**
     * For accounts
     */
    public static final String VALID_USERNAME_DEMO_ONE = "demo1";
    public static final String VALID_USERNAME_DEMO_TWO = "demo2";
    public static final String VALID_USERNAME_DEMO_THREE = "demo3";
    public static final String VALID_PASSWORD_DEMO_ONE = "1122qq";
    public static final String VALID_PASSWORD_DEMO_TWO = "22qqww";
    public static final String VALID_PASSWORD_DEMO_THREE = "abc!@#";

    public static final String INVALID_USERNAME = "demo 1";
    public static final String INVALID_PASSWORD = "11 22qq";

    public static final String PREFIX_WITH_VALID_USERNAME = " " + PREFIX_ID + VALID_USERNAME_DEMO_ONE;
    public static final String PREFIX_WITH_VALID_PASSWORD = " " + PREFIX_PASSWORD + VALID_PASSWORD_DEMO_ONE;
    public static final String PREFIX_WITH_VALID_NEW_PASSWORD = " " + PREFIX_NEW_PASSWORD + VALID_PASSWORD_DEMO_ONE;

    public static final String PREFIX_WITH_INVALID_USERNAME = " " + PREFIX_ID + INVALID_USERNAME;
    public static final String PREFIX_WITH_INVALID_PASSWORD = " " + PREFIX_PASSWORD + INVALID_PASSWORD;
    public static final String PREFIX_WITH_INVALID_NEW_PASSWORD = " " + PREFIX_NEW_PASSWORD + INVALID_PASSWORD;

    /**
     * For ingredients
     */
    public static final String VALID_NAME_APPLE = "Granny Smith Apple";
    public static final String VALID_NAME_BROCCOLI = "Australian Broccoli";
    public static final String VALID_UNIT_APPLE = "packet of 5";
    public static final String VALID_UNIT_BROCCOLI = "kilograms";
    public static final String VALID_PRICE_APPLE = "1.90";
    public static final String VALID_PRICE_BROCCOLI = "6.50";
    public static final int VALID_MINIMUM_APPLE = 3;
    public static final int VALID_MINIMUM_BROCCOLI = 5;
    public static final int VALID_NUMUNITS_APPLE = 10;
    public static final int VALID_NUMUNITS_BROCCOLI = 28;

    public static final String INGREDIENT_NAME_DESC_APPLE = " " + PREFIX_INGREDIENT_NAME + VALID_NAME_APPLE;
    public static final String INGREDIENT_NAME_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_NAME + VALID_NAME_BROCCOLI;
    public static final String INGREDIENT_UNIT_DESC_APPLE = " " + PREFIX_INGREDIENT_UNIT + VALID_UNIT_APPLE;
    public static final String INGREDIENT_UNIT_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_UNIT + VALID_UNIT_BROCCOLI;
    public static final String INGREDIENT_PRICE_DESC_APPLE = " " + PREFIX_INGREDIENT_PRICE + VALID_PRICE_APPLE;
    public static final String INGREDIENT_PRICE_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_PRICE + VALID_PRICE_BROCCOLI;
    public static final String INGREDIENT_MINIMUM_DESC_APPLE = " " + PREFIX_INGREDIENT_MINIMUM + VALID_MINIMUM_APPLE;
    public static final String INGREDIENT_MINIMUM_DESC_BROCCOLI = " " + PREFIX_INGREDIENT_MINIMUM
            + VALID_MINIMUM_BROCCOLI;

    public static final String INVALID_INGREDIENT_NAME_DESC = " " + PREFIX_INGREDIENT_NAME + "Chicken&"; // '&' not
    // allowed in ingredient names
    public static final String INVALID_INGREDIENT_UNIT_DESC = " " + PREFIX_INGREDIENT_UNIT + "kilogram+"; // '+' not
    // allowed in ingredient units
    public static final String INVALID_INGREDIENT_PRICE_DESC = " " + PREFIX_INGREDIENT_PRICE + "2.000"; // 3 decimal
    // places not allowed for ingredient prices
    public static final String INVALID_INGREDIENT_MINIMUM_DESC = " " + PREFIX_INGREDIENT_MINIMUM + "2.0"; // decimal
    // place not allowed for ingredient minimums

    /**
     * For menu
     */
    public static final String VALID_ITEM_NAME_BURGER = "Burger";
    public static final String VALID_ITEM_NAME_CHEESE_BURGER = "Cheese Burger";
    public static final String VALID_ITEM_NAME_FRIES = "Cheese Fries";
    public static final String VALID_ITEM_NAME_ICED_TEA = "Iced Tea";
    public static final String VALID_ITEM_PRICE_BURGER = "2.50";
    public static final String VALID_ITEM_PRICE_CHEESE_BURGER = "3";
    public static final String VALID_ITEM_PRICE_FRIES = "2";
    public static final String VALID_ITEM_PRICE_ICED_TEA = "1.50";
    public static final String VALID_ITEM_TAG_BURGER = "burger";
    public static final String VALID_ITEM_TAG_CHEESE = "cheese";
    public static final String VALID_ITEM_RECIPE_FRIES = "Deep fry potato and add cheese.";
    public static final String VALID_ITEM_PERCENT = "20";
    public static final List<XmlAdaptedTag> VALID_FRIES_TAGS = FRIES.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    public static final Map<String, String> VALID_EMPTY_REQUIRED_INGREDIENTS = new HashMap<>();

    public static final String ITEM_NAME_DESC_BURGER = " " + PREFIX_NAME + VALID_ITEM_NAME_BURGER;
    public static final String ITEM_NAME_DESC_CHEESE_BURGER = " " + PREFIX_NAME + VALID_ITEM_NAME_CHEESE_BURGER;
    public static final String ITEM_NAME_DESC_FRIES = " " + PREFIX_NAME + VALID_ITEM_NAME_FRIES;
    public static final String ITEM_NAME_DESC_ICED_TEA = " " + PREFIX_NAME + VALID_ITEM_NAME_ICED_TEA;
    public static final String ITEM_PRICE_DESC_BURGER = " " + PREFIX_PRICE + VALID_ITEM_PRICE_BURGER;
    public static final String ITEM_PRICE_DESC_CHEESE_BURGER = " " + PREFIX_PRICE + VALID_ITEM_PRICE_CHEESE_BURGER;
    public static final String ITEM_PRICE_DESC_FRIES = " " + PREFIX_PRICE + VALID_ITEM_PRICE_FRIES;
    public static final String ITEM_PRICE_DESC_ICED_TEA = " " + PREFIX_PRICE + VALID_ITEM_PRICE_ICED_TEA;
    public static final String ITEM_TAG_DESC_BURGER = " " + PREFIX_TAG + VALID_ITEM_TAG_BURGER;
    public static final String ITEM_TAG_DESC_CHEESE = " " + PREFIX_TAG + VALID_ITEM_TAG_CHEESE;
    public static final String ITEM_PERCENT_DESC = " " + PREFIX_PERCENT + VALID_ITEM_PERCENT;

    public static final String INVALID_ITEM_PRICE = "9.000"; // 3 decimal places not allowed
    public static final String INVALID_ITEM_TAG = "#hashTag";

    public static final String INVALID_ITEM_PERCENT_DESC = " " + PREFIX_PERCENT + "10000"; // at most 2 digits
    public static final String INVALID_ITEM_NAME_DESC = " " + PREFIX_NAME + "Fries&"; // '&' not allowed in names
    public static final String INVALID_ITEM_PRICE_DESC = " " + PREFIX_PRICE + INVALID_ITEM_PRICE;
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + INVALID_ITEM_TAG; // special character

    /**
     * For Reservation
     */
    public static final String VALID_RESERVATION_NAME_ANDREW = "Andrew";
    public static final String VALID_RESERVATION_NAME_BILLY = "Billy Bong";
    public static final String VALID_RESERVATION_PAX_ANDREW = "2";
    public static final String VALID_RESERVATION_PAX_BILLY = "4";
    //public static final String VALID_RESERVATION_DATETIME_ANDREW = "2018-12-03T12:00:00";
    //public static final String VALID_RESERVATION_DATETIME_BILLY = "2018-12-05T18:00:00";
    public static final String VALID_RESERVATION_DATE_ANDREW = "03-12-2019";
    public static final String VALID_RESERVATION_DATE_BILLY = "05-12-2019";
    public static final String VALID_RESERVATION_TIME_ANDREW = "12:00";
    public static final String VALID_RESERVATION_TIME_BILLY = "18:00";
    public static final String VALID_RESERVATION_REMARK_ANDREW = "Driving";
    public static final String VALID_RESERVATION_REMARK_BILLY = "Allergies";
    public static final String VALID_RESERVATION_TAG_ANDREW = "Driving";
    public static final String VALID_RESERVATION_TAG_BILLY = "Allergies";

    public static final String RESERVATION_NAME_DESC_ANDREW = " " + PREFIX_NAME + VALID_RESERVATION_NAME_ANDREW;
    public static final String RESERVATION_NAME_DESC_BILLY = " " + PREFIX_NAME + VALID_RESERVATION_NAME_BILLY;
    public static final String RESERVATION_PAX_DESC_ANDREW = " " + PREFIX_PAX + VALID_RESERVATION_PAX_ANDREW;
    public static final String RESERVATION_PAX_DESC_BILLY = " " + PREFIX_PAX + VALID_RESERVATION_PAX_BILLY;
    //public static final String RESERVATION_DATETIME_DESC_ANDREW =
    //        " " + PREFIX_DATETIME + VALID_RESERVATION_DATETIME_ANDREW;
    //public static final String RESERVATION_DATETIME_DESC_BILLY =
    //        " " + PREFIX_DATETIME + VALID_RESERVATION_DATETIME_BILLY;
    public static final String RESERVATION_DATE_DESC_ANDREW =
            " " + PREFIX_DATE + VALID_RESERVATION_DATE_ANDREW;
    public static final String RESERVATION_DATE_DESC_BILLY =
            " " + PREFIX_DATE + VALID_RESERVATION_DATE_BILLY;
    public static final String RESERVATION_TIME_DESC_ANDREW =
            " " + PREFIX_TIME + VALID_RESERVATION_TIME_ANDREW;
    public static final String RESERVATION_TIME_DESC_BILLY =
            " " + PREFIX_TIME + VALID_RESERVATION_TIME_BILLY;
    public static final String RESERVATION_TAG_DESC_ANDREW = " " + PREFIX_TAG + VALID_RESERVATION_TAG_ANDREW;
    public static final String RESERVATION_TAG_DESC_BILLY = " " + PREFIX_TAG + VALID_RESERVATION_TAG_BILLY;

    public static final String INVALID_RESERVATION_NAME_DESC = " " + PREFIX_NAME + "S&shrew"; // '&' not allowed
    public static final String INVALID_RESERVATION_PAX_DESC = " " + PREFIX_PAX + "a4"; // letters not allowed
    //public static final String INVALID_RESERVATION_DATETIME_DESC = " " + PREFIX_DATETIME + "2018-99"; // incomplete
    public static final String INVALID_RESERVATION_DATE_DESC = " " + PREFIX_DATE + "hi im a date"; // terrible joke
    public static final String INVALID_RESERVATION_TIME_DESC = " " + PREFIX_TIME + "hi im a time"; // bad joke

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final ChangePasswordCommand.EditAccountDescriptor DESC_DEMO_ONE;
    public static final ChangePasswordCommand.EditAccountDescriptor DESC_DEMO_TWO;

    public static final EditItemCommand.EditItemDescriptor DESC_BURGER;
    public static final EditItemCommand.EditItemDescriptor DESC_FRIES;

    public static final EditIngredientCommand.EditIngredientDescriptor DESC_APPLE;
    public static final EditIngredientCommand.EditIngredientDescriptor DESC_BROCCOLI;

    public static final EditReservationCommand.EditReservationDescriptor DESC_ANDREW;
    public static final EditReservationCommand.EditReservationDescriptor DESC_BILLY;

    public static final EditSalesCommand.EditRecordDescriptor DESC_RECORD_ONE;
    public static final EditSalesCommand.EditRecordDescriptor DESC_RECORD_TWO;

    static {
        // Account Management
        DESC_DEMO_ONE = new EditAccountDescriptorBuilder().withPassword(VALID_PASSWORD_DEMO_ONE).build();
        DESC_DEMO_TWO = new EditAccountDescriptorBuilder().withPassword(VALID_PASSWORD_DEMO_TWO).build();

        // Menu Management
        DESC_BURGER = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_BURGER)
                .withPrice(VALID_ITEM_PRICE_BURGER).build();
        DESC_FRIES = new EditItemDescriptorBuilder().withName(VALID_ITEM_NAME_FRIES).withPrice(VALID_ITEM_PRICE_FRIES)
                .withTags(VALID_ITEM_TAG_CHEESE).build();

        // Ingredient Management
        DESC_APPLE = new EditIngredientDescriptorBuilder().withName(VALID_NAME_APPLE)
                .withPrice(VALID_PRICE_APPLE).build();
        DESC_BROCCOLI =
                new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI).withPrice(VALID_PRICE_BROCCOLI)
                        .withUnit(VALID_UNIT_BROCCOLI).build();

        // Reservation Management
        DESC_ANDREW = new EditReservationDescriptorBuilder().withName(VALID_RESERVATION_NAME_ANDREW)
                .withPax(VALID_RESERVATION_PAX_ANDREW).withDate(VALID_RESERVATION_DATE_ANDREW)
                .withTime(VALID_RESERVATION_TIME_ANDREW).build();
        DESC_BILLY = new EditReservationDescriptorBuilder().withName(VALID_RESERVATION_NAME_BILLY)
                .withPax(VALID_RESERVATION_PAX_BILLY).withDate(VALID_RESERVATION_DATE_BILLY)
                .withTime(VALID_RESERVATION_TIME_BILLY).build();

        // Sales Management
        DESC_RECORD_ONE = new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_ONE)
                .withPrice(VALID_PRICE_RECORD_ONE).build();
        DESC_RECORD_TWO = new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_TWO)
                .withName(VALID_ITEM_NAME_RECORD_TWO).withPrice(VALID_PRICE_RECORD_TWO)
                .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO).build();

    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the restaurant book and the filtered list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RestaurantBook expectedRestaurantBook = new RestaurantBook(actualModel.getRestaurantBook());
        List<Item> expectedFilteredList = new ArrayList<>(actualModel.getFilteredItemList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedRestaurantBook, actualModel.getRestaurantBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredItemList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the item at the given {@code targetIndex} in the {@code
     * Model}'s restaurant book.
     */
    public static void showItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredItemList().size());

        Item item = model.getFilteredItemList().get(targetIndex.getZeroBased());
        final String[] splitName = item.getName().toString().split("\\s+");
        model.updateFilteredItemList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredItemList().size());
    }

    /**
     * Deletes the first item in {@code model}'s filtered list from {@code model}'s restaurant book.
     */
    public static void deleteFirstItem(Model model) {
        Item firstItem = model.getFilteredItemList().get(0);
        model.deleteItem(firstItem);
        model.commitRestaurantBook();
    }

    /**
     * Updates {@code model}'s filtered list to show only the ingredient at the given {@code targetIndex} in the {@code
     * model}'s restaurant book.
     */
    public static void showIngredientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredIngredientList().size());

        Ingredient ingredient = model.getFilteredIngredientList().get(targetIndex.getZeroBased());
        final String[] splitIngredient = ingredient.getName().toString().split("\\s+");
        model.updateFilteredIngredientList(
                new IngredientNameContainsKeywordsPredicate(Arrays.asList(splitIngredient[0])));

        assertEquals(1, model.getFilteredIngredientList().size());
    }
}
