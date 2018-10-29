package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ExportCertCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ExportCertCommand.
 */
public class ExportCertCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(new ExportCertCommand(INDEX_FIRST_PERSON), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased()));
    }

    @Test
    public void equals() {
        final ExportCertCommand standardCommand = new ExportCertCommand(INDEX_FIRST_PERSON);

        // different object, same value -> return true
        ExportCertCommand commandWithSameValue = new ExportCertCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValue));

        // same object, same values -> return true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> return false
        assertFalse(standardCommand.equals(null));

        // different type -> return false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different index value -> return false
        assertFalse(standardCommand.equals(new ExportCertCommand(INDEX_SECOND_VOLUNTEER)));
    }
}
