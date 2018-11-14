package seedu.parking.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_CAR_NUM;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_CAR_TYPE;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_COORD;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_FREE_PARK;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_LOTS_AVAILABLE;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_NIGHT_PARK;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_SHORT_TERM;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_TOTAL_LOTS;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_TYPE_PARK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.parking.commons.core.index.Index;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ADDRESS_JULIETT = "BLK 508-517,520-533 HOUGANG AVENUE 10";
    public static final String VALID_ADDRESS_KILO = "BLK 537-543, 564-569 PASIR RIS STREET 51";

    public static final String VALID_CARPARK_NUMBER_JULIETT = "HG38";
    public static final String VALID_CARPARK_NUMBER_KILO = "PR13";

    public static final String VALID_CARPARK_TYPE_JULIETT = "SURFACE CAR PARK";
    public static final String VALID_CARPARK_TYPE_KILO = "MULTI-STOREY CAR PARK";

    public static final String VALID_COORDINATE_JULIETT = "34274.4064, 39391.9731";
    public static final String VALID_COORDINATE_KILO = "40942.8203, 39055.5703";

    public static final String VALID_FREE_PARKING_JULIETT = "SUN & PH FR 7AM-10.30PM";
    public static final String VALID_FREE_PARKING_KILO = "NO";

    public static final String VALID_LOTS_AVAILABLE_JULIETT = "529";
    public static final String VALID_LOTS_AVAILABLE_KILO = "0";

    public static final String VALID_NIGHT_PARKING_JULIETT = "YES";
    public static final String VALID_NIGHT_PARKING_KILO = "YES";

    public static final String VALID_SHORT_TERM_JULIETT = "WHOLE DAY";
    public static final String VALID_SHORT_TERM_KILO = "WHOLE DAY";

    public static final String VALID_TOTAL_LOTS_JULIETT = "900";
    public static final String VALID_TOTAL_LOTS_KILO = "0";

    public static final String VALID_TYPE_OF_PARKING_JULIETT = "ELECTRONIC PARKING";
    public static final String VALID_TYPE_OF_PARKING_KILO = "ELECTRONIC PARKING";

    public static final String VALID_TAG_HOME = "Home";
    public static final String VALID_TAG_OFFICE = "Office";

    public static final String CARPARK_NO_DESC_JULIETT = " " + PREFIX_CAR_NUM + VALID_CARPARK_NUMBER_JULIETT;
    public static final String CARPARK_NO_DESC_KILO = " " + PREFIX_CAR_NUM + VALID_CARPARK_NUMBER_KILO;
    public static final String CARPARK_TYPE_DESC_JULIETT = " " + PREFIX_CAR_TYPE + VALID_CARPARK_TYPE_JULIETT;
    public static final String CARPARK_TYPE_DESC_KILO = " " + PREFIX_CAR_TYPE + VALID_CARPARK_TYPE_KILO;
    public static final String COORDINATE_DESC_JULIETT = " " + PREFIX_COORD + VALID_COORDINATE_JULIETT;
    public static final String COORDINATE_DESC_KILO = " " + PREFIX_COORD + VALID_COORDINATE_KILO;
    public static final String FREE_PARKING_DESC_JULIETT = " " + PREFIX_FREE_PARK + VALID_FREE_PARKING_JULIETT;
    public static final String FREE_PARKING_DESC_KILO = " " + PREFIX_FREE_PARK + VALID_FREE_PARKING_KILO;
    public static final String LOTS_AVAILABLE_DESC_JULIETT = " " + PREFIX_LOTS_AVAILABLE + VALID_LOTS_AVAILABLE_JULIETT;
    public static final String LOTS_AVAILABLE_DESC_KILO = " " + PREFIX_LOTS_AVAILABLE + VALID_LOTS_AVAILABLE_KILO;
    public static final String NIGHT_PARKING_DESC_JULIETT = " " + PREFIX_NIGHT_PARK + VALID_NIGHT_PARKING_JULIETT;
    public static final String NIGHT_PARKING_DESC_KILO = " " + PREFIX_NIGHT_PARK + VALID_NIGHT_PARKING_KILO;
    public static final String SHORT_TERM_DESC_JULIETT = " " + PREFIX_SHORT_TERM + VALID_SHORT_TERM_JULIETT;
    public static final String SHORT_TERM_DESC_KILO = " " + PREFIX_SHORT_TERM + VALID_SHORT_TERM_KILO;
    public static final String TOTAL_LOTS_DESC_KILO = " " + PREFIX_TOTAL_LOTS + VALID_TOTAL_LOTS_JULIETT;
    public static final String TOTAL_LOTS_DESC_JULIETT = " " + PREFIX_TOTAL_LOTS + VALID_TOTAL_LOTS_KILO;
    public static final String TYPE_OF_PARKING_DESC_JULIETT = " " + PREFIX_TYPE_PARK + VALID_TYPE_OF_PARKING_JULIETT;
    public static final String TYPE_OF_PARKING_DESC_KILO = " " + PREFIX_TYPE_PARK + VALID_TYPE_OF_PARKING_KILO;

    public static final String ADDRESS_DESC_JULIETT = " " + PREFIX_ADDRESS + VALID_ADDRESS_JULIETT;
    public static final String ADDRESS_DESC_KILO = " " + PREFIX_ADDRESS + VALID_ADDRESS_KILO;
    public static final String TAG_DESC_OFFICE = " " + PREFIX_TAG + VALID_TAG_OFFICE;
    public static final String TAG_DESC_HOME = " " + PREFIX_TAG + VALID_TAG_HOME;

    // '@' not allowed in carpark number.
    public static final String INVALID_CARPARK_NO_DESC = " " + PREFIX_CAR_NUM + "TJ@1";
    // '!@' not allowed in carpark type.
    public static final String INVALID_CARPARK_TYPE_DESC = " " + PREFIX_CAR_TYPE + "!@a";
    // wrong format, has number.
    public static final String INVALID_COORDINATE_DESC = " " + PREFIX_COORD + "asd!";
    // '!' not allowed
    public static final String INVALID_FREE_PARKING_DESC = " " + PREFIX_FREE_PARK + "!";
    // missing '@' symbol
    public static final String INVALID_LOTS_AVAILABLE_DESC = " " + PREFIX_LOTS_AVAILABLE + "asd";
    // missing '@' symbol
    public static final String INVALID_NIGHT_PARKING_DESC = " " + PREFIX_NIGHT_PARK + "%^&";
    // missing '@' symbol
    public static final String INVALID_SHORT_TERM_DESC = " " + PREFIX_SHORT_TERM + "!@#";
    // missing '@' symbol
    public static final String INVALID_TOTAL_LOTS_DESC = " " + PREFIX_TOTAL_LOTS + "asd!@#";
    // missing '@' symbol
    public static final String INVALID_TYPE_OF_PARKING_LOTS_DESC = " " + PREFIX_TYPE_PARK + "!@#";
    // empty string not allowed for addresses
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS;
    // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

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
     * - the car park finder and the filtered car park list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CarparkFinder expectedCarparkFinder = new CarparkFinder(actualModel.getCarparkFinder());
        List<Carpark> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCarparkList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedCarparkFinder, actualModel.getCarparkFinder());
            assertEquals(expectedFilteredList, actualModel.getFilteredCarparkList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the carpark at the given {@code targetIndex} in the
     * {@code model}'s car park finder.
     */
    public static void showCarparkAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCarparkList().size());
        Carpark carpark = model.getFilteredCarparkList().get(targetIndex.getZeroBased());
        final String[] splitName = carpark.getCarparkNumber().toString().split("\\s+");
        model.updateFilteredCarparkList(new CarparkContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCarparkList().size());
    }

    /**
     * Deletes the first carpark in {@code model}'s filtered list from {@code model}'s car park finder.
     */
    public static void deleteFirstCarpark(Model model) {
        Carpark firstCarpark = model.getFilteredCarparkList().get(0);
        model.deleteCarpark(firstCarpark);
        model.commitCarparkFinder();
    }

}
