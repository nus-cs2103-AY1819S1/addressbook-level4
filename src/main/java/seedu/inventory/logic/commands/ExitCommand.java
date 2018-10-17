package seedu.inventory.logic.commands;

import seedu.inventory.commons.core.EventsCenter;
import seedu.inventory.commons.events.ui.ExitAppRequestEvent;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Inventory Manager as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
