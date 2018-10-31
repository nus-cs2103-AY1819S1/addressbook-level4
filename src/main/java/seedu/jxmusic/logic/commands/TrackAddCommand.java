package seedu.jxmusic.logic.commands;

import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.player.JxMusicPlayer;

/**
 * Add a track to target playlist.
 */
public class TrackAddCommand extends Command {
    public static final String COMMAND_PHRASE = "track add";
    public static final String MESSAGE_SUCCESS = "";
    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Edits the details of the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_TRACK + "TRACK] "
        + "[" + PREFIX_PLAYLIST + "PLAYLIST]...\n"
        + "Example: " + COMMAND_PHRASE + " 1 "
        + PREFIX_TRACK + "rocknroll"
        + PREFIX_PLAYLIST + "rock";

    private Track trackToAdd;
    private Playlist targetPlaylist;

    public TrackAddCommand() {}

    public TrackAddCommand(Track trackToAdd, Playlist targetPlaylist) {
        this.trackToAdd = trackToAdd;
        this.targetPlaylist = targetPlaylist;
    }

    @Override
    public CommandResult execute(Model model) {
        new JxMusicPlayer();
        // get playlist
        // - check if playlist exists
        // add track to playlist
        // - check if track exists
        // - get track id or path
        // - check if track is already added
        // - update model
        // display success message
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
