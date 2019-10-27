package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalResumeModel;

public class MakeCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEntryCommand(null);
    }

    @Test
    public void noTemplateLoaded() {
        MakeCommand testCommand = new MakeCommand(Paths.get("test", "path"));
        assertEquals(testCommand.execute(new ModelManager(), EMPTY_COMMAND_HISTORY).feedbackToUser,
                MakeCommand.MESSAGE_NO_TEMPLATE_FAILURE);
    }

    @Test
    public void resumeSaved() {
        Path testPath = Paths.get("test", "path");
        MakeCommand testCommand = new MakeCommand(testPath);
        Model testModel = TypicalResumeModel.getDefaultTemplateModel();
        assertEquals(String.format(MakeCommand.MESSAGE_SUCCESS, testPath),
                testCommand.execute(testModel, EMPTY_COMMAND_HISTORY).feedbackToUser);
    }

    @Test
    public void equals() {
        Path testPath = Paths.get("test", "path");
        Path testPathTwo = Paths.get("not", "test", "path");
        MakeCommand testCommand = new MakeCommand(testPath);
        // same object -> returns true
        assertTrue(testCommand.equals(testCommand));

        // same values -> returns true
        assertTrue(testCommand.equals(new MakeCommand(testPath)));

        // different types -> returns false
        assertFalse(testCommand.equals(1));

        // null -> returns false
        assertFalse(testCommand.equals(null));

        // different person -> returns false
        assertFalse(testCommand.equals(new MakeCommand(testPathTwo)));
    }
}
