package seedu.restaurant.logic.commands.menu;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.menu.TodaySpecialCommand.preparePredicate;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import java.util.Calendar;
import java.util.Collections;

import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.menu.TagContainsKeywordsPredicate;
import seedu.restaurant.testutil.RestaurantBookBuilder;

//@@author yican95
/**
 * Contains integration tests (interaction with the Model) for {@code TodaySpecialCommand}.
 */
public class TodaySpecialCommandTest {
    private Model model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Calendar calendar = Calendar.getInstance();

    @Test
    public void execute_itemFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 1);
        // Sunday
        calendar.set(2018, Calendar.NOVEMBER, 4);
        TagContainsKeywordsPredicate predicate = preparePredicate(calendar);
        TodaySpecialCommand command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // Monday
        calendar.set(2018, Calendar.NOVEMBER, 5);
        predicate = preparePredicate(calendar);
        command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // Tuesday
        calendar.set(2018, Calendar.NOVEMBER, 6);
        predicate = preparePredicate(calendar);
        command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // Wednesday
        calendar.set(2018, Calendar.NOVEMBER, 7);
        predicate = preparePredicate(calendar);
        command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // Thursday
        calendar.set(2018, Calendar.NOVEMBER, 8);
        predicate = preparePredicate(calendar);
        command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // Friday
        calendar.set(2018, Calendar.NOVEMBER, 9);
        predicate = preparePredicate(calendar);
        command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // Saturday
        calendar.set(2018, Calendar.NOVEMBER, 10);
        predicate = preparePredicate(calendar);
        command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        //leap day
        calendar.set(2016, Calendar.FEBRUARY, 29);
        predicate = preparePredicate(calendar);
        command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noItemFound() {
        RestaurantBook ab = new RestaurantBookBuilder().build();
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(ab, new UserPrefs());
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = preparePredicate(calendar);
        TodaySpecialCommand command = new TodaySpecialCommand(calendar);
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredItemList());
    }

}
