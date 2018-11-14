package seedu.parking.logic.commands;

import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.logic.commands.CommandTestUtil.showCarparkAtIndex;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;

import java.io.IOException;

import org.junit.Before;

import org.junit.Test;

import seedu.parking.logic.CommandHistory;

import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() throws IOException {
        model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
        expectedModel = new ModelManager(model.getCarparkFinder(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        String messageSuccess = String.format(ListCommand.MESSAGE_SUCCESS,
                model.getCarparkFinder().getCarparkList().size());
        assertCommandSuccess(new ListCommand(), model, commandHistory, messageSuccess, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCarparkAtIndex(model, INDEX_FIRST_CARPARK);
        String messageSuccess = String.format(ListCommand.MESSAGE_SUCCESS,
                model.getCarparkFinder().getCarparkList().size());
        assertCommandSuccess(new ListCommand(), model, commandHistory, messageSuccess, expectedModel);
    }
}
