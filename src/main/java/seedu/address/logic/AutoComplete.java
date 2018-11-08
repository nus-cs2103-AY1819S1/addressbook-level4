package seedu.address.logic;

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
import seedu.address.logic.commands.ListModuleCommand;
import seedu.address.logic.commands.ListOccasionCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

/**
 * Provides basic logic of giving complete command suggestions.
 *
 * @author alistair
 */
public class AutoComplete {

    private final String[] allCommands = { AddPersonCommand.COMMAND_WORD, AddModuleCommand.COMMAND_WORD,
        AddOccasionCommand.COMMAND_WORD, EditPersonCommand.COMMAND_WORD, EditModuleCommand.COMMAND_WORD,
        EditOccasionCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD, FindPersonCommand.COMMAND_WORD, FindModuleCommand.COMMAND_WORD,
        FindOccasionCommand.COMMAND_WORD, ListPersonCommand.COMMAND_WORD, ListModuleCommand.COMMAND_WORD,
        ListOccasionCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD};

    private String[] personList;
    private String[] moduleList;
    private String[] occasionList;
    private String[] suggestionsList;
    private Model model;

    public AutoComplete(Model model) {
        this.model = model;
        suggestionsList = allCommands;
    }

    /**
     * Getter to return list of suggestions for autocomplete.
     * @return array of strings of suggestions.
     */
    public String[] getSuggestionsList() {
        return suggestionsList;
    }

}
