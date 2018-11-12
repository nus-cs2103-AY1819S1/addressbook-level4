package seedu.address.logic.commands.jio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.jio.JioTestUtil.DINNER;
import static seedu.address.model.jio.JioTestUtil.LUNCH;
import static seedu.address.model.jio.JioTestUtil.LUNCH_COPY;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Name;
import seedu.address.model.UserPrefs;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Address;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;
import seedu.address.model.user.Username;
import seedu.address.testutil.TypicalUsers;
import seedu.address.testutil.UserBuilder;

public class DeleteJioCommandTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), TypicalUsers.getTypicalUserData(),
            new UserBuilder().build());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullJio_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteJioCommand(null);
    }

    @Test
    public void execute_validJio_success() {
        Jio jioToDelete = model.getJioList().get(0);
        DeleteJioCommand deleteJioCommand = new DeleteJioCommand(jioToDelete.getName());

        String expectedMessage = String.format(DeleteJioCommand.MESSAGE_SUCCESS, jioToDelete.getName());

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                TypicalUsers.getTypicalUserData(), new UserBuilder().build());

        assertCommandSuccess(deleteJioCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidJio_throwsCommandException() {
        Name jioName = new seedu.address.model.Name("invalid");
        Date dinnerDate = new Date(new Week("2"), new Day("tue"), new Time("1800"));
        Address dinnerAddress = new Address("foodclique");
        Username creator = TypicalUsers.getTypicalUsers().get(0).getUsername();

        Jio invalidJio = new Jio(jioName, dinnerDate, dinnerAddress, creator);
        DeleteJioCommand deleteCommand = new DeleteJioCommand(invalidJio.getName());

        assertCommandFailure(deleteCommand, model, commandHistory, DeleteJioCommand.MESSAGE_NONEXISTENT_JIO);
    }

    @Test
    public void equals() {
        DeleteJioCommand deleteLunchCommand = new DeleteJioCommand(LUNCH.getName());
        DeleteJioCommand deleteDinnerCommand = new DeleteJioCommand(DINNER.getName());

        // same object -> returns true
        assertTrue(deleteLunchCommand.equals(deleteLunchCommand));

        // same values -> returns true
        DeleteJioCommand deleteLunchCommandCopy = new DeleteJioCommand(LUNCH_COPY.getName());
        assertTrue(deleteLunchCommand.equals(deleteLunchCommandCopy));

        // different types -> returns false
        assertFalse(deleteLunchCommand.equals(1));

        // null -> returns false
        assertFalse(deleteLunchCommand.equals(null));

        // different restaurant -> returns false
        assertFalse(deleteLunchCommand.equals(deleteDinnerCommand));
    }

}
