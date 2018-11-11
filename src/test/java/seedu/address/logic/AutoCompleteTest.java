package seedu.address.logic;

import static junit.framework.TestCase.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.AddOccasionCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.commands.EditOccasionCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindModuleCommand;
import seedu.address.logic.commands.FindOccasionCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.InsertPersonCommand;
import seedu.address.logic.commands.ListModuleCommand;
import seedu.address.logic.commands.ListOccasionCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemovePersonCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AutoCompleteTest {
    private AutoComplete autoCompleter;
    private String[] allCommands = {AddPersonCommand.COMMAND_WORD, AddModuleCommand.COMMAND_WORD,
        AddOccasionCommand.COMMAND_WORD, EditPersonCommand.COMMAND_WORD, EditModuleCommand.COMMAND_WORD,
        EditOccasionCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD, FindPersonCommand.COMMAND_WORD, FindModuleCommand.COMMAND_WORD,
        FindOccasionCommand.COMMAND_WORD, ListPersonCommand.COMMAND_WORD, ListModuleCommand.COMMAND_WORD,
        ListOccasionCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD,
        InsertPersonCommand.COMMAND_WORD, RemovePersonCommand.COMMAND_WORD};

    @Before
    public void setUp() {
        Model model = new ModelManager();
        autoCompleter = new AutoComplete(model);
    }

    @Test
    public void equals() {
        assertTrue(Arrays.equals(autoCompleter.getSuggestionsList(), allCommands));
    }
}
