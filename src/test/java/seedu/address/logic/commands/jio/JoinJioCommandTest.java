package seedu.address.logic.commands.jio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.jio.JioTestUtil.DINNER;
import static seedu.address.model.jio.JioTestUtil.LUNCH;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalUsers;
import seedu.address.testutil.UserBuilder;

public class JoinJioCommandTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), TypicalUsers.getTypicalUserData(),
            new UserBuilder().build());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullJioName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new JoinJioCommand(null);
    }

    @Test
    public void execute_nonexistentJio_failure() throws Exception {
        thrown.expect(CommandException.class);
        JoinJioCommand joinJioCommand = new JoinJioCommand(LUNCH.getName());
        joinJioCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_alreadyJoinedJio_failure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("You have already joined this jio");

        model.loginUser(TypicalUsers.getTypicalUsers().get(0));
        assertTrue(model.getJioList().get(0).hasUser(TypicalUsers.getTypicalUsers().get(0)));

        JoinJioCommand joinJioCommand = new JoinJioCommand(model.getJioList().get(0).getName());
        joinJioCommand.execute(model, commandHistory);
    }

    @Test
    public void execute() throws Exception {
        assertFalse(LUNCH.hasUser(TypicalUsers.getTypicalUsers().get(5)));

        model.loginUser(TypicalUsers.getTypicalUsers().get(5));
        JoinJioCommand joinJioCommand = new JoinJioCommand(LUNCH.getName());
        joinJioCommand.execute(model, commandHistory);

        assertTrue(model.getJioList().stream().anyMatch(jio -> (jio.equals(LUNCH)
                && jio.hasUser(TypicalUsers.getTypicalUsers().get(5)))));
    }

    @Test
    public void equals() {
        JoinJioCommand joinLunchCommand = new JoinJioCommand(LUNCH.getName());
        JoinJioCommand joinDinnerCommand = new JoinJioCommand(DINNER.getName());

        // same object -> returns true
        assertTrue(joinLunchCommand.equals(joinLunchCommand));

        // same values -> returns true
        JoinJioCommand addLunchCommandCopy = new JoinJioCommand(LUNCH.getName());
        assertTrue(joinLunchCommand.equals(addLunchCommandCopy));

        // different types -> returns false
        assertFalse(joinLunchCommand.equals(1));

        // null -> returns false
        assertFalse(joinLunchCommand.equals(null));

        // different restaurant -> returns false
        assertFalse(joinLunchCommand.equals(joinDinnerCommand));
    }
}
