package seedu.parking.logic.commands;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.events.ui.ExitAppRequestEvent;
import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";


    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Car Park Finder as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
