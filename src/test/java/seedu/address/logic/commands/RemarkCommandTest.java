package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

public class RemarkCommandTest {
    Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(),new UserPrefs());

    @Test
    public void execute_invalidCommandFormat_failure() {
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, "test remark");
        assertCommandFailure(remarkCommand, model, INDEX_FIRST_PERSON +" "+"test remark");
    }
}
