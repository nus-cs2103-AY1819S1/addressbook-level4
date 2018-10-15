package seedu.scheduler.logic.commands;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

//TODO:implement the class
/**
 * Get events from google calendar.
 */
public class GetGoogleEventsCommand extends Command {
    public static final String COMMAND_WORD = "ggevents";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Get google calendar events.\n"
            + "download the events from primary google calendar.\n"
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SYNC_SUCCESS = "Events in google calendar downloaded.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
