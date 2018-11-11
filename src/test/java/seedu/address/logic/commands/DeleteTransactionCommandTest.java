package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_NON_EXISTENT_CCA;
import static seedu.address.testutil.TypicalCcas.getTypicalBudgetBook;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.transaction.Entry;

//@@author ericyjw
public class DeleteTransactionCommandTest {

    private Model model = new ModelManager(getTypicalBudgetBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        // Target entry: first cca, first transaction entry
        Cca cca = model.getFilteredCcaList().get(0);
        Integer entryNum = 1;
        Entry entryToDelete = cca.getEntry(entryNum);

        // Add Carl and David into the model
        model.addPerson(CARL);
        model.commitAddressBook();
        model.addPerson(DANIEL);
        model.commitAddressBook();

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(cca.getName(), entryNum);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
            entryToDelete);

        ModelManager expectedModel = new ModelManager(new AddressBook(), model.getBudgetBook(), new UserPrefs(),
            model.getExistingEmails());
        Cca updatedCca = cca.removeTransaction(entryToDelete);
        expectedModel.updateCca(cca, updatedCca);
        expectedModel.commitBudgetBook();

        // Add Carls and David into the expected model
        expectedModel.addPerson(CARL);
        expectedModel.commitAddressBook();
        expectedModel.addPerson(DANIEL);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteTransactionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCcaUnfilteredList_throwsCommandException() throws CommandException {
        // Target entry: first cca, first transaction entry
        Cca cca = model.getFilteredCcaList().get(0);
        Integer entryNum = 1;

        CcaName invalidCcaName = new CcaName("Softball"); // softball not in budget book

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(invalidCcaName, entryNum);

        String expectedMessage = MESSAGE_NON_EXISTENT_CCA;

        assertCommandFailure(deleteTransactionCommand, model, commandHistory, expectedMessage);

        deleteTransactionCommand = new DeleteTransactionCommand(cca.getName(), cca.getEntrySize() + 1);

        expectedMessage = Messages.MESSAGE_INVALID_TRANSACTION_INDEX;
        assertCommandFailure(deleteTransactionCommand, model, commandHistory, expectedMessage);
    }
}
