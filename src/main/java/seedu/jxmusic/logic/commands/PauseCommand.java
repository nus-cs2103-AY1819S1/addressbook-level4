package seedu.jxmusic.logic.commands;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.PlayerManager;

/**
 * Pauses track that is currently playing
 */
public class PauseCommand extends Command {

    public static final String COMMAND_WORD = "pause";

    public static final String MESSAGE_SUCCESS = "Pause a track";


    @Override
    public CommandResult execute(Model model) {
        PlayerManager.getInstance().pause();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
