package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.List;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

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
    public static final String MESSAGE_DUPLICATE_TRACK = "This track already exists in the playlist";
    public static final String MESSAGE_PLAYLIST_DOES_NOT_EXIST = "This playlist does not exist";

    private Track trackToAdd;
    private Playlist targetPlaylist;

    public TrackAddCommand(Track trackToAdd, Playlist targetPlaylist) {
        requireNonNull(trackToAdd);
        requireNonNull(targetPlaylist);
        this.trackToAdd = trackToAdd;
        this.targetPlaylist = targetPlaylist;
    }

    /**
     * @param targetPlaylist
     * @return playlistCopy
     */
    private Playlist copyPlaylist(Playlist targetPlaylist) {
        List<Track> tracks = targetPlaylist.getTracks();
        Name nameCopy = targetPlaylist.getName();
        Playlist playlistCopy = new Playlist(nameCopy);
        for (Track track : tracks) {
            playlistCopy.addTrack(track);
        }
        return playlistCopy;
    }

    /**
     * @param playlist
     * @param newTrack
     * @return
     */
    private boolean trackExists(Playlist playlist, Track newTrack) {
        for (Track track : playlist.getTracks()) {
            if (track.getFileName().equalsIgnoreCase(newTrack.getFileName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CommandResult execute(Model model) {
        Playlist updatedPlaylist;

        // - check if playlist exists
        if (!model.hasPlaylist(targetPlaylist)) {
            return new CommandResult(MESSAGE_PLAYLIST_DOES_NOT_EXIST);
        }
        updatedPlaylist = copyPlaylist(targetPlaylist);
        if (trackExists(targetPlaylist, trackToAdd)) {
            return new CommandResult(MESSAGE_DUPLICATE_TRACK);
        }
        updatedPlaylist.addTrack(trackToAdd);
        model.updatePlaylist(targetPlaylist, updatedPlaylist);
        // add track to playlist
        // - check if track exists
        // - get track id or path
        // - check if track is already added
        // - update model
        // display success message
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
