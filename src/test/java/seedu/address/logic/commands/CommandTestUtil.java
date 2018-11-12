package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.restaurant.NameContainsKeywordsPredicate;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Reviews;
import seedu.address.model.restaurant.UserReview;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.user.Username;
import seedu.address.testutil.EditRestaurantDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final UserReview VALID_USER_REVIEW = new UserReview(new Username("navekom"),
            new Rating(3), new WrittenReview("I tried the Ultimate Beef Sushi"
            + " Experience and they were delicious."));
    public static final Reviews VALID_WAA_COW_REVIEWS = new Reviews("3.00", 3.0,
            Collections.singletonList(VALID_USER_REVIEW));

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_RATING = "5";
    public static final String RATING_A = PREFIX_RATING + VALID_RATING;
    public static final String VALID_REVIEW = "The Ban Mian was absolutely delicious.";
    public static final String REVIEW_A = PREFIX_REVIEW + VALID_REVIEW;

    public static final String INVALID_RATING = "100";
    public static final String INVALID_RATING_A = PREFIX_RATING + INVALID_RATING;
    public static final String INVALID_REVIEW = " ";
    public static final String INVALID_REVIEW_A = PREFIX_REVIEW + INVALID_REVIEW;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditRestaurantDescriptor DESC_AMY;
    public static final EditCommand.EditRestaurantDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditRestaurantDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditRestaurantDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the address book and the filtered restaurant list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Restaurant> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRestaurantList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredRestaurantList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the restaurant at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showRestaurantAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRestaurantList().size());

        Restaurant restaurant = model.getFilteredRestaurantList().get(targetIndex.getZeroBased());
        final String[] splitName = restaurant.getName().fullName.split("\\s+");
        model.updateFilteredRestaurantList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRestaurantList().size());
    }

    /**
     * Deletes the first restaurant in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstRestaurant(Model model) {
        Restaurant firstRestaurant = model.getFilteredRestaurantList().get(0);
        model.deleteRestaurant(firstRestaurant);
        model.commitAddressBook();
    }

}
