package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Add a track to target playlist.
 */
public class TrackAddCommand extends Command {
    public static final String COMMAND_PHRASE = "track add";
    public static final String MESSAGE_SUCCESS = "Tracks added to playlist: %1$s -> %2$s";
    public static final String MESSAGE_USAGE_TRACK = COMMAND_PHRASE
            + ": Adds a new track(s) "
            + "by the name of the track and playlist.\n"
            + "Playlist will be modified with new track.\n"
            + "Parameters: [" + PREFIX_PLAYLIST + "PLAYLIST] "
            + "[" + PREFIX_TRACK + "TRACK]...\n"
            + "Example: " + COMMAND_PHRASE + " "
            + PREFIX_PLAYLIST + "rockPlaylist" + " "
            + PREFIX_TRACK + "rockNRoll";
    public static final String MESSAGE_USAGE_INDEX = COMMAND_PHRASE
            + ": Adds a new track(s) "
            + "by the index of the track and name of the playlist.\n"
            + "Playlist will be modified with new track at track index.\n"
            + "Parameters: [" + PREFIX_PLAYLIST + "PLAYLIST] "
            + "[" + PREFIX_INDEX + "INDEX]...\n"
            + "Example: " + COMMAND_PHRASE + " "
            + PREFIX_PLAYLIST + "rockPlaylist" + " "
            + PREFIX_INDEX + "1";
    public static final String MESSAGE_TOO_MANY_PREFIX = "Too many prefixes, either use "
            + PREFIX_TRACK + " or "
            + PREFIX_INDEX;
    public static final String MESSAGE_TRACK_DOES_NOT_EXIST = "These tracks do not exist: %3$s";
    public static final String MESSAGE_INDEX_DOES_NOT_EXIST = "These indexes do not exist: %3$s";
    public static final String MESSAGE_PLAYLIST_DOES_NOT_EXIST = "Playlist does not exist: %1$s";

    private List<Track> argTracksToAdd;
    private Playlist argPlaylist;
    private Playlist updatedPlaylist;
    private Playlist actualPlaylist;
    private Track actualTrack = null;
    private List<Index> argIndexesToAdd;
    private List<Track> tracksToAdd = new ArrayList<Track>();
    private List<Track> tracksNotAdded = new ArrayList<Track>();
    private ArrayList<String> indexesNotAdded = new ArrayList<>();

    public TrackAddCommand(Playlist targetPlaylist, Track... trackToAdd) {
        requireNonNull(trackToAdd);
        requireNonNull(targetPlaylist);
        this.argPlaylist = targetPlaylist;
        this.argTracksToAdd = new ArrayList<Track>();
        for (Track track : trackToAdd) {
            this.argTracksToAdd.add(track);
        }
    }

    public TrackAddCommand(Playlist targetPlaylist, InputType type, List tracksToAdd) {
        requireNonNull(tracksToAdd);
        requireNonNull(targetPlaylist);
        this.argPlaylist = targetPlaylist;
        if (type == InputType.TRACK) {
            this.argTracksToAdd = tracksToAdd;
        }
        if (type == InputType.INDEX) {
            this.argIndexesToAdd = tracksToAdd;
        }
    }

    private boolean isExistingIndex(Index i, int trackListSize) {
        return (i.getOneBased() <= trackListSize);
    }

    private List<Track> getTracksFromIndexes(Model model, ArrayList<Index> indexes) {
        List<Track> trackList = new ArrayList<>();
        final ObservableList<Track> viewedTracks = model.getFilteredTrackList();
        System.out.println(viewedTracks);
        int trackListSize = viewedTracks.size();
        for (Index i : indexes) {
            if (isExistingIndex(i, trackListSize)) {
                Track track = viewedTracks.get(i.getZeroBased());
                trackList.add(track);
            }
        }
        return trackList;
    }

    private ArrayList<String> getInvalidIndexes(int trackListSize, ArrayList<Index> indexes) {
        ArrayList<String> invalidIndexes = new ArrayList<>();
        for (Index i : indexes) {
            if (!isExistingIndex(i, trackListSize)) {
                invalidIndexes.add("" + i.getOneBased());
            }
        }
        return invalidIndexes;
    }

    private boolean isExistingPlaylist(Model model, Playlist existingPlaylist) {
        return !model.hasPlaylist(argPlaylist);
    }

    @Override
    public CommandResult execute(Model model) {
        int viewedTracksSize = model.getFilteredTrackList().size();
        ObservableSet<Track> libraryTracks = model.getLibrary().getTracks();
        ObservableList<Playlist> libraryPlaylists = model.getLibrary().getPlaylistList();

        // check if playlist exists
        if (isExistingPlaylist(model, argPlaylist)) {
            return new CommandResult(String.format(MESSAGE_PLAYLIST_DOES_NOT_EXIST, argPlaylist.getName()));
        }

        actualPlaylist = libraryPlaylists.filtered(playlist
            -> playlist.isSamePlaylist(argPlaylist))
            .get(0);
        updatedPlaylist = actualPlaylist.copy();

        // checks if index is being used instead of track
        if (argTracksToAdd == null) {
            ArrayList<Index> indexesToAdd = new ArrayList<Index>(argIndexesToAdd);
            argTracksToAdd = getTracksFromIndexes(model, indexesToAdd);
            indexesNotAdded = getInvalidIndexes(viewedTracksSize, indexesToAdd);
        }

        // check if tracks exist and add tracks to playlist
        for (Track trackToAdd : argTracksToAdd) {
            Optional<Track> listOfTracks = libraryTracks.stream()
                    .filter(track -> track.equals(trackToAdd))
                    .findFirst();
            boolean areExistingTracks = listOfTracks.isPresent();
            if (areExistingTracks) {
                tracksToAdd.add(listOfTracks.get());
            } else {
                // to display as tracks that cannot be added
                tracksNotAdded.add(trackToAdd);
            }
        }
        for (Track trackToAdd : tracksToAdd) {
            actualTrack = libraryTracks.stream()
                    .filter(track -> track.equals(trackToAdd))
                    .findFirst()
                    .get();
            updatedPlaylist.addTrack(actualTrack);
        }
        model.updatePlaylist(actualPlaylist, updatedPlaylist);

        // check if indexes do not exist
        if (indexesNotAdded.isEmpty() && tracksNotAdded.isEmpty()) {
            if (tracksToAdd.size() == 1) {
                return new CommandResult(String.format(MESSAGE_SUCCESS, tracksToAdd.get(0), actualPlaylist.getName()));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, tracksToAdd, actualPlaylist.getName()));
        }

        if (!indexesNotAdded.isEmpty() && !tracksToAdd.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS + "\n" + MESSAGE_INDEX_DOES_NOT_EXIST,
                    tracksToAdd, actualPlaylist.getName(), indexesNotAdded));
        }
        if (!indexesNotAdded.isEmpty() && tracksToAdd.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_INDEX_DOES_NOT_EXIST, "", "", indexesNotAdded));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS + "\n" + MESSAGE_TRACK_DOES_NOT_EXIST,
                tracksToAdd, actualPlaylist.getName(), tracksNotAdded));
    }

    /**
     * Allows selection of add track by Index, or add track by Track Name
     */
    public enum InputType {
        INDEX, TRACK;
    }

}
