package seedu.jxmusic.logic.commands;

import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.JxMusicPlayer;

/**
 * Lists all persons in the jxmusic book to the user.
 */
public class PlayPlaylistCommand extends Command {

    // todo change to "play p/" when parser can use p/ for parameter
    public static final String COMMAND_WORD = "play";

    public static final String MESSAGE_SUCCESS = "Play a playlist";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new JxMusicPlayer().play();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
