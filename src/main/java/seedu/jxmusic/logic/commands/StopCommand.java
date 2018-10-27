package seedu.jxmusic.logic.commands;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.PlayerManager;

/**
 * Lists all persons in the jxmusic book to the user.
 */
public class StopCommand extends Command {

    // todo change to "play p/" when parser can use p/ for parameter
    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_SUCCESS = "Stop the play of current track";


    @Override
    public CommandResult execute(Model model) {
        PlayerManager.getInstance().stop();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
