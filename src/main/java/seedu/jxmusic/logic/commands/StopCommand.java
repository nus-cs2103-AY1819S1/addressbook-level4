package seedu.jxmusic.logic.commands;

import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.Playable;

/**
 * Lists all persons in the jxmusic book to the user.
 */
public class StopCommand extends Command {

    // todo change to "play p/" when parser can use p/ for parameter
    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_SUCCESS = "Stop the play of current track";

    public static final String MESSAGE_NO_TRACK = "No track to stop";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (player.getStatus() == Playable.Status.UNINITIALIZED) {
            throw new CommandException(MESSAGE_NO_TRACK);
        }
        player.stop();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
