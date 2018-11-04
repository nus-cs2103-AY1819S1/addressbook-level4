package seedu.jxmusic.logic.commands;

import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.Playable;

/**
 * Pauses track that is currently playing
 */
public class PauseCommand extends Command {

    public static final String COMMAND_WORD = "pause";

    public static final String MESSAGE_SUCCESS = "Pause a track";

    public static final String MESSAGE_NOT_PLAYING = "No playing track to pause";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (player.getStatus() == Playable.Status.PLAYING) {
            player.pause();
        } else {
            throw new CommandException(MESSAGE_NOT_PLAYING);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
