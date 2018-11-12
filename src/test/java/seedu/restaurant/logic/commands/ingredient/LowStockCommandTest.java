package seedu.restaurant.logic.commands.ingredient;

import static seedu.restaurant.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.restaurant.logic.commands.CommandTestUtil.showIngredientAtIndex;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.ModelManager;
import seedu.restaurant.model.UserPrefs;
import seedu.restaurant.model.ingredient.Ingredient;

//@@author rebstan97
/**
 * Contains integration tests (interaction with the Model) and unit tests for LowStockCommand.
 */
public class LowStockCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Predicate<Ingredient> predicate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRestaurantBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        predicate = ingredient ->
                ingredient.getNumUnits().getNumberOfUnits() < ingredient.getMinimum().getMinimumUnit();
    }

    @Test
    public void execute_listIsNotFiltered_showsUpdatedFilteredList() {
        model.updateFilteredIngredientList(predicate);
        assertCommandSuccess(new LowStockCommand(), model, commandHistory,
                LowStockCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsUpdatedFilteredList() {
        showIngredientAtIndex(model, INDEX_FIRST);
        model.updateFilteredIngredientList(predicate);
        assertCommandSuccess(new LowStockCommand(), model, commandHistory,
                LowStockCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
