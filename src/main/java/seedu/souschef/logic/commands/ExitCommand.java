package seedu.souschef.logic.commands;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.events.ui.ExitAppRequestEvent;
import seedu.souschef.logic.History;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(History history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
