package seedu.address.logic.commands.jio;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.jio.JioTestUtil.MALA;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.jio.Jio;

import seedu.address.testutil.TypicalUsers;
import seedu.address.testutil.UserBuilder;

public class CreateJioCommandIntegrationTest {
    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), TypicalUsers.getTypicalUserData(),
                new UserBuilder().build());
    }

    @Test
    public void execute_newJio_success() {
        Jio validJio = MALA;

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                TypicalUsers.getTypicalUserData(), new UserBuilder().build());
        expectedModel.createJio(validJio);

        assertCommandSuccess(new CreateJioCommand(validJio), model, commandHistory,
                String.format(CreateJioCommand.MESSAGE_SUCCESS, validJio), expectedModel);
    }

    @Test
    public void execute_duplicateJio_throwsCommandException() {
        Jio jioInList = model.getJioList().get(0);
        assertCommandFailure(new CreateJioCommand(jioInList), model, commandHistory,
                CreateJioCommand.MESSAGE_DUPLICATE_JIO);
    }

}
