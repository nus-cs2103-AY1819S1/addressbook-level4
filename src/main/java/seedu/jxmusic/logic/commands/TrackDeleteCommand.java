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
 * Remove a track from target playlist.
 */
public class TrackRemoveCommand extends Command {
    public static final String COMMAND_PHRASE = "track del";
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
     * For immutability
     * @param targetPlaylist
     * Returns a copy of targetPlaylist
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
     * @param playlist an existing playlist
     * @param newTrack a new track to be added
     * Returns boolean depending if newTrack already exists in playlist
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

        // check if playlist exists
        if (!model.hasPlaylist(targetPlaylist)) {
            return new CommandResult(MESSAGE_PLAYLIST_DOES_NOT_EXIST);
        }
        updatedPlaylist = copyPlaylist(targetPlaylist);
        // check if track exists in existing playlist
        if (trackExists(targetPlaylist, trackToAdd)) {
            return new CommandResult(MESSAGE_DUPLICATE_TRACK);
        }
        updatedPlaylist.addTrack(trackToAdd);
        model.updatePlaylist(targetPlaylist, updatedPlaylist);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
