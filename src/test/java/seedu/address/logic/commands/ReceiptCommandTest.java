package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Patient;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ReceiptCommand}.
 */

public class ReceiptCommandTest {
    //model here is required just for accessing data base of patients
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    //change the name of the test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientToGenerateReceiptFor = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReceiptCommand receiptCommand = new ReceiptCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ReceiptCommand.MESSAGE_SUCCESS, patientToGenerateReceiptFor);

        //change this as there is no need to update model
        assertCommandSuccess(receiptCommand, model, commandHistory, expectedMessage, model);
    }
}
