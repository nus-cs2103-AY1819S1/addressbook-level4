package seedu.address.logic.commands;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SuggestCommandEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Not a text command. Can only be triggered using {tab} in CommandBox.
 * Suggests a list of commands based on prefix matching.
 */
public class SuggestCommand extends Command {

    public static final String[] COMMAND_WORDS = {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
            SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD};

    private final String[] suggestions;

    public SuggestCommand(String prefix) {
        if (!prefix.matches("[a-zA-Z]+")) {
            suggestions = new String[0];
            return;
        }
        suggestions = Arrays.stream(COMMAND_WORDS).filter(s
            -> s.matches("^" + prefix + ".*")).toArray(String[]::new);
    }

    public boolean isPrefixValid() {
        return suggestions.length > 0;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        //guaranteed at least one commandWord in commandWords, otherwise exception raised by parser
        StringBuilder builder = new StringBuilder("Do you mean the following commands:\n");
        for(String s : suggestions) {
            builder.append(s);
            builder.append(", ");
        }
        //remove excess ", "
        builder.deleteCharAt(builder.length() - 1);
        builder.deleteCharAt(builder.length() - 1);

        EventsCenter.getInstance().post(new SuggestCommandEvent(suggestions));
        return new CommandResult(builder.toString());
    }
}
