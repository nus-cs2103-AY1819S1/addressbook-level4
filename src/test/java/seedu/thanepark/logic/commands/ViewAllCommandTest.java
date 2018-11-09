package seedu.thanepark.logic.commands;

import static seedu.thanepark.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Before;
import org.junit.Test;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAllCommand.
 */
public class ViewAllCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalThanePark(), new UserPrefs());
        expectedModel = new ModelManager(model.getThanePark(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW,
                expectedModel.getFilteredRideList().size());
        assertCommandSuccess(new ViewAllCommand(), model, commandHistory,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW,
                expectedModel.getFilteredRideList().size());
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ViewAllCommand(), model, commandHistory,
                expectedMessage, expectedModel);
    }
}
