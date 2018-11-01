package seedu.jxmusic.logic.commands;

import javafx.util.Duration;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.player.PlayerManager;

/**
 * Seeks the player to a new playback time.
 */

public class SeekCommand extends Command {

    public static final String COMMAND_WORD = "seek";
    //should we change it to ake this can display the time? but if yes, we need to abort the static while i am not sure
    //of the effect of static
    public static final String MESSAGE_SUCCESS = "seek the player to time required";

    //time that is required to seek to
    private Duration time;

    public SeekCommand(double milliseconds) {
        this.time = new Duration(milliseconds);
    }


    @Override
    public CommandResult execute(Model model) {
        PlayerManager.getInstance().seek(time);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
