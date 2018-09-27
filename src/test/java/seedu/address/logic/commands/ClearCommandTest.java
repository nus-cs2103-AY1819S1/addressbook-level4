package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.user.Username;
import seedu.address.testutil.ModelUtil;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        try {
            Model model = ModelUtil.modelWithTestUser();
            Model expectedModel = ModelUtil.modelWithTestUser();
            expectedModel.commitAddressBook();

            assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS,
                    expectedModel);
        } catch (NoUserSelectedException e) {
            Assert.fail("Error committing, No user selected in model.");
        }
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        try {
            expectedModel.resetData(new AddressBook(new Username("typicalAddressBook")));
            expectedModel.commitAddressBook();
        } catch (NoUserSelectedException e) {
            Assert.fail("Error committing, No user selected in model.");
        }

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
