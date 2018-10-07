package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.player.JxMusicPlayer;

/**
 * Lists all persons in the address book to the user.
 */
public class StopCommand extends Command {

    // todo change to "play p/" when parser can use p/ for parameter
    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_SUCCESS = "Stop the play of current track";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new JxMusicPlayer().stop();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
