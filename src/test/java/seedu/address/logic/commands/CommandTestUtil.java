package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAR_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAR_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREE_PARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOTS_AVAILABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NIGHT_PARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHORT_TERM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_LOTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE_PARK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkContainsKeywordsPredicate;
//import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ADDRESS_1 = "BLK 508-517,520-533 HOUGANG AVENUE 10";
    public static final String VALID_ADDRESS_2 = "BLK 537-543, 564-569 PASIR RIS STREET 51";

    public static final String VALID_CARPARK_NUMBER_1 = "HG38";
    public static final String VALID_CARPARK_NUMBER_2 = "PR13";

    public static final String VALID_CARPARK_TYPE_1 = "SURFACE CAR PARK";
    public static final String VALID_CARPARK_TYPE_2 = "SURFACE CAR PARK";

    public static final String VALID_COORDINATE_1 = "34274.4064, 39391.9731";
    public static final String VALID_COORDINATE_2 = "40942.8203, 39055.5703";

    public static final String VALID_FREE_PARKING_1 = "SUN & PH FR 7AM-10.30PM";
    public static final String VALID_FREE_PARKING_2 = "NO";

    public static final String VALID_LOTS_AVAILABLE_1 = "529";
    public static final String VALID_LOTS_AVAILABLE_2 = "0";

    public static final String VALID_NIGHT_PARKING_1 = "YES";
    public static final String VALID_NIGHT_PARKING_2 = "YES";

    public static final String VALID_SHORT_TERM_1 = "WHOLE DAY";
    public static final String VALID_SHORT_TERM_2 = "WHOLE DAY";

    public static final String VALID_TOTAL_LOTS_1 = "900";
    public static final String VALID_TOTAL_LOTS_2 = "0";

    public static final String VALID_TYPE_OF_PARKING_1 = "ELECTRONIC PARKING";
    public static final String VALID_TYPE_OF_PARKING_2 = "ELECTRONIC PARKING";

    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String CARPARK_NO_DESC_1 = " " + PREFIX_CAR_NUM + VALID_CARPARK_NUMBER_1;
    public static final String CARPARK_NO_DESC_2 = " " + PREFIX_CAR_NUM + VALID_CARPARK_NUMBER_2;
    public static final String CARPARK_TYPE_DESC_1 = " " + PREFIX_CAR_TYPE + VALID_CARPARK_TYPE_1;
    public static final String CARPARK_TYPE_DESC_2 = " " + PREFIX_CAR_TYPE + VALID_CARPARK_TYPE_2;
    public static final String COORDINATE_DESC_1 = " " + PREFIX_COORD + VALID_COORDINATE_1;
    public static final String COORDINATE_DESC_2 = " " + PREFIX_COORD + VALID_COORDINATE_2;
    public static final String FREE_PARKING_DESC_1 = " " + PREFIX_FREE_PARK + VALID_FREE_PARKING_1;
    public static final String FREE_PARKING_DESC_2 = " " + PREFIX_FREE_PARK + VALID_FREE_PARKING_2;
    public static final String LOTS_AVAILABLE_DESC_1 = " " + PREFIX_LOTS_AVAILABLE + VALID_LOTS_AVAILABLE_1;
    public static final String LOTS_AVAILABLE_DESC_2 = " " + PREFIX_LOTS_AVAILABLE + VALID_LOTS_AVAILABLE_2;
    public static final String NIGHT_PARKING_DESC_1 = " " + PREFIX_NIGHT_PARK + VALID_NIGHT_PARKING_1;
    public static final String NIGHT_PARKING_DESC_2 = " " + PREFIX_NIGHT_PARK + VALID_NIGHT_PARKING_2;
    public static final String SHORT_TERM_DESC_1 = " " + PREFIX_SHORT_TERM + VALID_SHORT_TERM_1;
    public static final String SHORT_TERM_DESC_2 = " " + PREFIX_SHORT_TERM + VALID_SHORT_TERM_2;
    public static final String TOTAL_LOTS_DESC_2 = " " + PREFIX_TOTAL_LOTS + VALID_TOTAL_LOTS_1;
    public static final String TOTAL_LOTS_DESC_1 = " " + PREFIX_TOTAL_LOTS + VALID_TOTAL_LOTS_2;
    public static final String TYPE_OF_PARKING_DESC_1 = " " + PREFIX_TYPE_PARK + VALID_TYPE_OF_PARKING_1;
    public static final String TYPE_OF_PARKING_DESC_2 = " " + PREFIX_TYPE_PARK + VALID_TYPE_OF_PARKING_2;

    public static final String ADDRESS_DESC_1 = " " + PREFIX_ADDRESS + VALID_ADDRESS_1;
    public static final String ADDRESS_DESC_2 = " " + PREFIX_ADDRESS + VALID_ADDRESS_2;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_CARPARK_NO_DESC = " " + PREFIX_CAR_NUM + "TJ@1"; // '@' not allowed in carpark number.
    public static final String INVALID_CARPARK_TYPE_DESC = " " + PREFIX_CAR_TYPE + "!@a"; // '!@' not allowed in carpark type.
    public static final String INVALID_COORDINATE_DESC = " " + PREFIX_COORD + "asd!"; // wrong format, has number.
    public static final String INVALID_FREE_PARKING_DESC = " " + PREFIX_FREE_PARK + "!"; // '!' not allowed
    public static final String INVALID_LOTS_AVAILABLE_DESC = " " + PREFIX_LOTS_AVAILABLE + "asd"; // missing '@' symbol
    public static final String INVALID_NIGHT_PARKING_DESC = " " + PREFIX_NIGHT_PARK + "%^&"; // missing '@' symbol
    public static final String INVALID_SHORT_TERM_DESC = " " + PREFIX_SHORT_TERM + "!@#"; // missing '@' symbol
    public static final String INVALID_TOTAL_LOTS_DESC = " " + PREFIX_TOTAL_LOTS + "asd!@#"; // missing '@' symbol
    public static final String INVALID_TYPE_OF_PARKING_LOTS_DESC = " " + PREFIX_TYPE_PARK + "!@#"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

//    public static final EditCommand.EditCarparkDescriptor DESC_AMY;
//    public static final EditCommand.EditCarparkDescriptor DESC_BOB;
//
//    static {
//        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_CARPARK_NUMBER_1)
//                .withPhone(VALID_CARPARK_TYPE_1).withEmail(VALID_COORDINATE_1).withAddress(VALID_ADDRESS_1)
//                .withTags(VALID_TAG_FRIEND).build();
//        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_CARPARK_NUMBER_2)
//                .withPhone(VALID_CARPARK_TYPE_2).withEmail(VALID_COORDINATE_2).withAddress(VALID_ADDRESS_2)
//                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
//    }

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
     * - the address book and the filtered carpark list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Carpark> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCarparkList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredCarparkList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the carpark at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCarparkAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCarparkList().size());
        Carpark carpark = model.getFilteredCarparkList().get(targetIndex.getZeroBased());
        final String[] splitName = carpark.getCarparkNumber().value.split("\\s+");
        model.updateFilteredCarparkList(new CarparkContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCarparkList().size());
    }

    /**
     * Deletes the first carpark in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstCarpark(Model model) {
        Carpark firstCarpark = model.getFilteredCarparkList().get(0);
        model.deleteCarpark(firstCarpark);
        model.commitAddressBook();
    }

}
