package seedu.restaurant.logic.commands;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.ExitAppRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String COMMAND_ALIAS = "ex";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Restaurant Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
