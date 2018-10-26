package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_SET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simplejavamail.email.Email;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.DefaultEmailBuilder;

//@@author EatOrBeEaten
public class ComposeEmailIndexCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ComposeEmailIndexCommand(null, INDEX_SET);
    }

    @Test
    public void constructor_nullIndexSet_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Email validEmail = new DefaultEmailBuilder().buildWithoutTo();
        new ComposeEmailIndexCommand(validEmail, null);
    }

    @Test
    public void execute_emailAccepted_composeSuccessful() throws Exception {
        Email validEmail = new DefaultEmailBuilder().buildWithoutTo();

        CommandResult commandResult = new ComposeEmailIndexCommand(validEmail, INDEX_SET)
                .execute(model, commandHistory);

        assertEquals(String.format(ComposeEmailIndexCommand.MESSAGE_SUCCESS, validEmail.getSubject()),
                commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Email meeting = new DefaultEmailBuilder().withSubject("Meeting").buildWithoutTo();
        Email conference = new DefaultEmailBuilder().withSubject("Conference").buildWithoutTo();
        ComposeEmailIndexCommand composeMeetingCommand = new ComposeEmailIndexCommand(meeting, INDEX_SET);
        ComposeEmailIndexCommand composeConferenceCommand = new ComposeEmailIndexCommand(conference, INDEX_SET);

        // same object -> returns true
        assertTrue(composeMeetingCommand.equals(composeMeetingCommand));

        // same values -> returns true
        ComposeEmailIndexCommand composeMeetingCommandCopy = new ComposeEmailIndexCommand(meeting, INDEX_SET);
        assertTrue(composeMeetingCommand.equals(composeMeetingCommandCopy));

        // different types -> returns false
        assertFalse(composeMeetingCommand.equals(1));

        // null -> returns false
        assertFalse(composeMeetingCommand.equals(null));

        // different email -> returns false
        assertFalse(composeMeetingCommand.equals(composeConferenceCommand));
    }

}
