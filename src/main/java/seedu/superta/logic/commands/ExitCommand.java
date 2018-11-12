package seedu.superta.logic.commands;

import seedu.superta.commons.core.EventsCenter;
import seedu.superta.commons.events.ui.ExitAppRequestEvent;
import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting SuperTA as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
