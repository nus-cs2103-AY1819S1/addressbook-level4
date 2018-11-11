package seedu.restaurant.logic.commands.sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.sales.ItemNameContainsKeywordsPredicate;
import seedu.restaurant.model.sales.SalesRecord;

/**
 * Contains helper methods for testing commands.
 */
public class SalesCommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the restaurant book and the filtered record list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {

        RestaurantBook expectedRestaurantBook = new RestaurantBook(actualModel.getRestaurantBook());
        List<SalesRecord> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecordList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedRestaurantBook, actualModel.getRestaurantBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredRecordList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the sales record at the given {@code targetIndex} in the
     * {@code model}'s restaurant book.
     */
    public static void showRecordAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecordList().size());

        SalesRecord salesRecord = model.getFilteredRecordList().get(targetIndex.getZeroBased());
        final String[] splitRecord = salesRecord.getName().toString().split("\\s+");
        model.updateFilteredRecordList(new ItemNameContainsKeywordsPredicate(Arrays.asList(splitRecord[0])));

        assertEquals(1, model.getFilteredRecordList().size());
    }
}
