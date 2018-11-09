package seedu.restaurant.logic.commands.menu;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.menu.SortMenuCommand.MESSAGE_SORTED;
import static seedu.restaurant.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.restaurant.testutil.menu.TypicalItems.BEEF_BURGER;

import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.testutil.RestaurantBookBuilder;

//@@author yican95
/**
 * Contains integration tests (interaction with the Model) for {@code SortMenuCommand}.
 */
public class SortMenuCommandTest {
    private RestaurantBook ab = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(APPLE_JUICE).build();
    private Model model = new ModelManager(ab, new UserPrefs());
    private Model expectedModel = new ModelManager(ab, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_sortMenuByName() {
        SortMethod sortMethod = SortMethod.NAME;
        String expectedMessage = String.format(MESSAGE_SORTED, sortMethod.name());
        SortMenuCommand command = new SortMenuCommand(sortMethod);
        expectedModel.sortMenu(sortMethod);
        expectedModel.commitRestaurantBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortMenuByPrice() {
        SortMethod sortMethod = SortMethod.PRICE;
        String expectedMessage = String.format(MESSAGE_SORTED, sortMethod.name());
        SortMenuCommand command = new SortMenuCommand(sortMethod);
        expectedModel.sortMenu(sortMethod);
        expectedModel.commitRestaurantBook();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
