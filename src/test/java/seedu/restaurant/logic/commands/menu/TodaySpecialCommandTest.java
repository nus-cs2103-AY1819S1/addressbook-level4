package seedu.restaurant.logic.commands.menu;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.commons.core.Messages.MESSAGE_ITEMS_LISTED_OVERVIEW;
import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.menu.TodaySpecialCommand.preparePredicate;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

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

    @Test
    public void execute_itemFound() {
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 1);
        TagContainsKeywordsPredicate predicate = preparePredicate();
        TodaySpecialCommand command = new TodaySpecialCommand();
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noItemFound() {
        RestaurantBook ab = new RestaurantBookBuilder().build();
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(ab, new UserPrefs());
        String expectedMessage = String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = preparePredicate();
        TodaySpecialCommand command = new TodaySpecialCommand();
        expectedModel.updateFilteredItemList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredItemList());
    }

}
