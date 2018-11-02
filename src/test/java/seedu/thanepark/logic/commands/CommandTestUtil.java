package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.commands.exceptions.CommandException;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.RideContainsKeywordsPredicate;
import seedu.thanepark.testutil.UpdateRideDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_MAINTENANCE_AMY = "11111111";
    public static final String VALID_MAINTENANCE_BOB = "22222222";
    public static final String VALID_WAIT_TIME_AMY = "1";
    public static final String VALID_WAIT_TIME_BOB = "2";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String MAINTENANCE_DESC_AMY = " " + PREFIX_MAINTENANCE + VALID_MAINTENANCE_AMY;
    public static final String MAINTENANCE_DESC_BOB = " " + PREFIX_MAINTENANCE + VALID_MAINTENANCE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_WAITING_TIME + VALID_WAIT_TIME_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_WAITING_TIME + VALID_WAIT_TIME_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ZONE + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ZONE + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    // 'a' not allowed in days since maintenance
    public static final String INVALID_MAINTENANCE_DESC = " " + PREFIX_MAINTENANCE + "911a";
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_WAITING_TIME + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ZONE; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final UpdateCommand.UpdateRideDescriptor DESC_AMY;
    public static final UpdateCommand.UpdateRideDescriptor DESC_BOB;

    static {
        DESC_AMY = new UpdateRideDescriptorBuilder().withName(VALID_NAME_AMY)
                .withMaintenance(VALID_MAINTENANCE_AMY).withWaitTime(VALID_WAIT_TIME_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new UpdateRideDescriptorBuilder().withName(VALID_NAME_BOB)
                .withMaintenance(VALID_MAINTENANCE_BOB).withWaitTime(VALID_WAIT_TIME_BOB).withAddress(VALID_ADDRESS_BOB)
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
     * - the thanepark book and the filtered ride list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ThanePark expectedAddressBook = new ThanePark(actualModel.getThanePark());
        List<Ride> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRideList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getThanePark());
            assertEquals(expectedFilteredList, actualModel.getFilteredRideList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the ride at the given {@code targetIndex} in the
     * {@code model}'s thanepark book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRideList().size());

        Ride ride = model.getFilteredRideList().get(targetIndex.getZeroBased());
        final String[] splitName = ride.getName().fullName.split("\\s+");
        model.updateFilteredRideList(new RideContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRideList().size());
    }

    /**
     * Deletes the first ride in {@code model}'s filtered list from {@code model}'s thanepark book.
     */
    public static void deleteFirstPerson(Model model) {
        Ride firstRide = model.getFilteredRideList().get(0);
        model.deleteRide(firstRide);
        model.commitThanePark();
    }

}
