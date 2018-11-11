package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.thanepark.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Test;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * MaintainCommand.
 */
public class MaintainCommandTest {

    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidRideIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        MaintainCommand maintainCommand = new MaintainCommand(outOfBoundIndex);

        assertCommandFailure(maintainCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    /**
     * maintain ride where index is larger than size of filtered list,
     * but smaller than size of thanepark book
     */
    @Test
    public void execute_invalidRideIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_RIDE;
        // ensures that outOfBoundIndex is still in bounds of thanepark list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getThanePark().getRideList().size());

        MaintainCommand maintainCommand = new MaintainCommand(outOfBoundIndex);

        assertCommandFailure(maintainCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        MaintainCommand maintainCommand = new MaintainCommand(outOfBoundIndex);

        // execution failed -> thanepark state not added into model
        assertCommandFailure(maintainCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        // single thanepark state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final MaintainCommand standardCommand = new MaintainCommand(INDEX_FIRST_PERSON);

        // same values -> returns true
        MaintainCommand commandWithSameValues = new MaintainCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MaintainCommand(INDEX_SECOND_RIDE)));
    }
}
