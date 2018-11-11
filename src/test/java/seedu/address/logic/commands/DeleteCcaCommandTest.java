package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;

//@@author ericyjw

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCcaCommand}.
 */
public class DeleteCcaCommandTest {

    private Model model = new ModelManager(getTypicalBudgetBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDeletingOfCca_success() {
        Cca ccaToDelete = model.getFilteredCcaList().get(0);
        CcaName ccaName = ccaToDelete.getName();
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(ccaName);

        String expectedMessage = String.format(DeleteCcaCommand.MESSAGE_DELETE_CCA_SUCCESS, ccaToDelete);

        ModelManager expectedModel = new ModelManager(new AddressBook(), model.getBudgetBook(), new UserPrefs(),
            model.getExistingEmails());
        expectedModel.deleteCca(ccaToDelete);
        expectedModel.commitBudgetBook();

        assertCommandSuccess(deleteCcaCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDeletingOfCca_throwsCommandException() {
        CcaName ccaTobeDeleted = new CcaName("Soccer");
        DeleteCcaCommand deleteCcaCommand = new DeleteCcaCommand(ccaTobeDeleted);

        assertCommandFailure(deleteCcaCommand, model, commandHistory, Messages.MESSAGE_INVALID_CCA);
    }

    @Test
    public void equals() {
        DeleteCcaCommand deleteFirstCommand = new DeleteCcaCommand(new CcaName("Soccer"));
        DeleteCcaCommand deleteSecondCommand = new DeleteCcaCommand(new CcaName("Hockey"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCcaCommand deleteFirstCommandCopy = new DeleteCcaCommand(new CcaName("Soccer"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no cca.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredCcaList(p -> false);

        assertTrue(model.getFilteredCcaList().isEmpty());
    }
}
