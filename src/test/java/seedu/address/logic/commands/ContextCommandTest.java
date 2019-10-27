package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EntryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.util.SampleDataUtil;

public class ContextCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private final Awareness typicalAwareness = SampleDataUtil.getSampleAwareness();

    @Test
    public void constructor_nullExpression_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ContextCommand(null);
    }

    @Test
    public void constructor_validExpression_success() {
        String expression = "  orbital  ";
        assertEquals(new ContextCommand("orbital"), new ContextCommand(expression.trim()));
    }

    @Test
    public void execute_noMatchingResumeEntry_unableToAddResumeEntry() throws Exception {
        Model model = new ModelManager(new EntryBook(), new UserPrefs(), typicalAwareness);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ContextCommand.MESSAGE_NO_RESUME_ENTRY, "double degree programme"));
        String result = new ContextCommand("ddp").execute(model, commandHistory).feedbackToUser;

    }

    @Test
    public void execute_matchingResumeEntry_successfulAdd() throws Exception {
        Model model = new ModelManager(new EntryBook(), new UserPrefs(), typicalAwareness);

        String taResult = new ContextCommand("ta ma1101r").execute(model, commandHistory).feedbackToUser;
        assertEquals(taResult, String.format(AddEntryCommand.MESSAGE_SUCCESS, SampleDataUtil.MA1101R_TA));

        String cs2103tResult = new ContextCommand("cs2103t").execute(model, commandHistory).feedbackToUser;
        assertEquals(cs2103tResult, String.format(AddEntryCommand.MESSAGE_SUCCESS, SampleDataUtil.NUS_CS2103T));
    }

    @Test
    public void execute_twiceSameExpression_duplicateEntry() throws Exception {
        Model model = new ModelManager(new EntryBook(), new UserPrefs(), typicalAwareness);

        String taResult = new ContextCommand("ta ma1101r").execute(model, commandHistory).feedbackToUser;
        assertEquals(taResult, String.format(AddEntryCommand.MESSAGE_SUCCESS, SampleDataUtil.MA1101R_TA));

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEntryCommand.MESSAGE_DUPLICATE_ENTRY);
        new ContextCommand("ta ma1101r").execute(model, commandHistory);

    }

    @Test
    public void execute_differentExpression_duplicateEntry() throws Exception {
        Model model = new ModelManager(new EntryBook(), new UserPrefs(), typicalAwareness);

        String taResult = new ContextCommand("ta ma1101r").execute(model, commandHistory).feedbackToUser;
        assertEquals(taResult, String.format(AddEntryCommand.MESSAGE_SUCCESS, SampleDataUtil.MA1101R_TA));

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEntryCommand.MESSAGE_DUPLICATE_ENTRY);
        new ContextCommand("teach asst ma1101r").execute(model, commandHistory);

    }

}
