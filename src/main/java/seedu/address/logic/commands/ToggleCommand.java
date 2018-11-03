package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Toggles between the various GUI tabs.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";
    public static final String COMMAND_WORD_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_WORD_ALIAS + ")"
            + ": Toggles the GUI tab identified by name of the tab.\n"
            + "Parameters: TAB (must be a valid tab name)\n"
            + "Example: " + COMMAND_WORD + " events";

    public static final String MESSAGE_TOGGLE_SUCCESS = "Toggled to :";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_TOGGLE_SUCCESS));
    }
}
