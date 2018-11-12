package seedu.jxmusic.logic.commands;

import javafx.util.Duration;

import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;

/**
 * Plays a track/playlist or continue playing from a pause/stop
 */
public class DurationCommand extends Command {

    public static final String COMMAND_PHRASE = "duration";

    public static final String MESSAGE_SUCCESS = "Duration of current track: %1$s";
    public static final String MESSAGE_FAILURE = "There is no track playing/paused/stopped";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (player.getStatus()) {
        case PLAYING:
        case PAUSED:
        case STOPPED:
            Duration fileDuration = player.getDuration();
            int totalSeconds = (int) fileDuration.toSeconds();
            int seconds = totalSeconds % 60;
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds - seconds - hours * 3600) / 60;
            String durationString = String.format("%02dh %02dm %02ds", hours, minutes, seconds);
            return new CommandResult(String.format(MESSAGE_SUCCESS, durationString));
        default:
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
