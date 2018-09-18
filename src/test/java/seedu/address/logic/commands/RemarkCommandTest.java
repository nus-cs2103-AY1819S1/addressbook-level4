package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_1;
import static seedu.address.logic.commands.CommandTestUtil.SAMPLE_REMARK_2;

public class RemarkCommandTest {
    Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(),new UserPrefs());

    @Test
    public void execute_invalidCommandFormat_failure() {
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, SAMPLE_REMARK_1);
        assertCommandFailure(remarkCommand, model, INDEX_FIRST_PERSON +" "+SAMPLE_REMARK_1);
    }

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
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, SAMPLE_REMARK_1)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, SAMPLE_REMARK_2)));
    }
}
