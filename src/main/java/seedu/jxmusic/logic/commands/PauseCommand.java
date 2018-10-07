package seedu.jxmusic.logic.commands;

import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.JxMusicPlayer;

/**
 * Pauses track that is currently playing
 */
public class PauseCommand extends Command {

    public static final String COMMAND_WORD = "pause";

    public static final String MESSAGE_SUCCESS = "Pause a track";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new JxMusicPlayer().pause();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
