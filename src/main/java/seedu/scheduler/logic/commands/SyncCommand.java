package seedu.scheduler.logic.commands;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

//TODO:implement the class
/**
 * Sync the events.
 */
public class SyncCommand extends Command {
    public static final String COMMAND_WORD = "sync";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sync the events ";

    public static final String MESSAGE_SYNC_SUCCESS = "Synced";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
