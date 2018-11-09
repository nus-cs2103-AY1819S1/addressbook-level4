package seedu.jxmusic.logic.commands;

import javafx.util.Duration;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.Playable;

/**
 * Seeks the player to a new playback time.
 */

public class SeekCommand extends Command {

    public static final String COMMAND_PHRASE = "seek";

    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": seek the play point to the specified time point.\n"
            + "Parameters: d/TIME"
            + "TIME is in the format of  in the format of [[h ]m ]s each of which "
            + "represents a unit of time that will be summed up to get the time point.\n";

    public static final String MESSAGE_SUCCESS = "seek the player to time required";

    public static final String MESSAGE_NOT_PLAYING = "No playing track to seek";

    //time that is required to seek to
    private Duration time;

    public SeekCommand(Duration seekTime) {
        this.time = seekTime;
    }

    public Duration getTime() {
        return time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (player.getStatus() == Playable.Status.PLAYING || player.getStatus() == Playable.Status.PAUSED) {
            player.seek(time);
        } else {
            throw new CommandException(MESSAGE_NOT_PLAYING);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

