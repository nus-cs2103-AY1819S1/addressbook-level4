package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_REMARK_ADD_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

public class RemarkCommandTest {
    Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(),new UserPrefs());

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, SAMPLE_REMARK_1);
        final RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,SAMPLE_REMARK_1);

        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(prepareCommand(INDEX_SECOND_PERSON, SAMPLE_REMARK_1)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(prepareCommand(INDEX_FIRST_PERSON, SAMPLE_REMARK_2)));
    }

    /**
     * Returns a {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        //remarkCommand.setData(model, new CommandHistory());
        return remarkCommand;
    }
}
