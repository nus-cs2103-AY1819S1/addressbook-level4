package seedu.jxmusic.logic.commands;

import javafx.util.Duration;

import seedu.jxmusic.model.Model;

/**
 * Seeks the player to a new playback time.
 */

public class SeekCommand extends Command {

    public static final String COMMAND_PHRASE = "seek";

    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": seek the playpoint to the specified time point.\n"
            + "Parameters: ti/TIME"
            + "TIME is in the format of  in the format of [[h ]m ]s each of which "
            + "represents a unit of time that will be summed up to get the time point.\n";

    public static final String MESSAGE_SUCCESS = "seek the player to time required:";



    //time that is required to seek to
    private Duration time;

    public SeekCommand(Duration seekTime) {
        this.time = seekTime;
    }


    @Override
    public CommandResult execute(Model model) {
        player.seek(time);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
