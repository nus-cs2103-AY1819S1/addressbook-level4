package seedu.thanepark.logic.commands;

import seedu.thanepark.commons.core.EventsCenter;
import seedu.thanepark.commons.events.ui.ExitAppRequestEvent;
import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
