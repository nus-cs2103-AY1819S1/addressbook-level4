package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Delete a track from target playlist.
 */
public class TrackDeleteCommand extends Command {
    public static final String COMMAND_PHRASE = "track del";
    public static final String MESSAGE_SUCCESS = "Track %1$s deleted from playlist %2$s";
    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Deletes a track from the playlist identified "
            + "by the index of track in the playlist."
            + "Track will be deleted from the playlist.\n"
            + "Parameters: [" + PREFIX_PLAYLIST + "PLAYLIST] + [INDEX]...\n"
            + "Example: " + COMMAND_PHRASE
            + PREFIX_PLAYLIST + "rockPlaylist"
            + "1";
    public static final String MESSAGE_TRACK_DOES_NOT_EXIST = "This playlist does not have track: %1$s";
    public static final String MESSAGE_PLAYLIST_DOES_NOT_EXIST = "This playlist %1$s does not exist";

    private Track trackToDelete;
    private Playlist targetPlaylist;

    public TrackDeleteCommand(Track trackToDelete, Playlist targetPlaylist) {
        requireNonNull(trackToDelete);
        requireNonNull(targetPlaylist);
        this.trackToDelete = trackToDelete;
        this.targetPlaylist = targetPlaylist;
    }

    @Override
    public CommandResult execute(Model model) {
        Playlist updatedPlaylist;

        // check if playlist exists
        if (!model.hasPlaylist(targetPlaylist)) {
            return new CommandResult(String.format(MESSAGE_PLAYLIST_DOES_NOT_EXIST, targetPlaylist.getName()));
        }
        updatedPlaylist = targetPlaylist.copy();
        // check if track exists in existing playlist
        if (!targetPlaylist.hasTrack(trackToDelete)) {
            return new CommandResult(String.format(MESSAGE_TRACK_DOES_NOT_EXIST, trackToDelete));
        }
        if (updatedPlaylist.deleteTrack(trackToDelete)) {
            model.updatePlaylist(targetPlaylist, updatedPlaylist);
            return new CommandResult(String.format(MESSAGE_SUCCESS, trackToDelete, targetPlaylist.getName()));
        }
        return null;
    }

}
