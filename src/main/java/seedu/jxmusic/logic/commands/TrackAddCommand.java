package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Add a track to target playlist.
 */
public class TrackAddCommand extends Command {
    public static final String COMMAND_PHRASE = "track add";
    public static final String MESSAGE_SUCCESS = "Track added to playlist: %1$s -> %2$s";
    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Adds a new track to the playlist identified "
        + "by the name of the track and playlist. "
        + "Playlist will be modified with new track.\n"
        + "Parameters: [" + PREFIX_PLAYLIST + "PLAYLIST] "
        + "[" + PREFIX_TRACK + "TRACK]...\n"
        + "Example: " + COMMAND_PHRASE + " "
        + PREFIX_PLAYLIST + "rockPlaylist "
        + PREFIX_TRACK + "rockNRoll";
    public static final String MESSAGE_DUPLICATE_TRACK = "Track already exists in the playlist: %1$s";
    public static final String MESSAGE_TRACK_DOES_NOT_EXIST = "Track does not exist: %1$s";
    public static final String MESSAGE_PLAYLIST_DOES_NOT_EXIST = "Playlist does not exist: %1$s";

    private Track argTrackToAdd;
    private Playlist argPlaylist;

    public TrackAddCommand(Track trackToAdd, Playlist targetPlaylist) {
        requireNonNull(trackToAdd);
        requireNonNull(targetPlaylist);
        this.argTrackToAdd = trackToAdd;
        this.argPlaylist = targetPlaylist;
    }

    @Override
    public CommandResult execute(Model model) {
        Playlist updatedPlaylist;
        Playlist actualPlaylist;
        Track trackToAdd = null;

        // check if playlist exists
        if (!model.hasPlaylist(argPlaylist)) {
            return new CommandResult(String.format(MESSAGE_PLAYLIST_DOES_NOT_EXIST, argPlaylist.getName()));
        }

        // violates law of demeter for read operation due to best access path to Playlist
        actualPlaylist = model.getLibrary().getPlaylistList()
                .filtered(playlist -> playlist.isSamePlaylist(argPlaylist))
                .get(0);

        updatedPlaylist = actualPlaylist;

        // check if track exists
        if (!model.getLibrary().getTracks().contains(argTrackToAdd)) {
            return new CommandResult(String.format(MESSAGE_TRACK_DOES_NOT_EXIST, argTrackToAdd
                    .getFileNameWithoutExtension()));
        }

        // check if track exists in existing playlist
        if (actualPlaylist.hasTrack(argTrackToAdd)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE_TRACK, argTrackToAdd));
        }

        trackToAdd = model.getLibrary().getTracks().stream().filter(track -> track
                .equals(argTrackToAdd)).findFirst().get();

        updatedPlaylist.addTrack(trackToAdd);
        model.updatePlaylist(actualPlaylist, updatedPlaylist);
        return new CommandResult(String.format(MESSAGE_SUCCESS, trackToAdd, actualPlaylist.getName()));
    }

}
