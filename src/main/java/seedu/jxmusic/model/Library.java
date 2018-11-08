package seedu.jxmusic.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 * Wraps all data at the library level
 * Duplicates are not allowed (by .isSamePlaylist comparison)
 */
public class Library implements ReadOnlyLibrary {

    public static final String LIBRARYDIR = "library/";
    private final UniquePlaylistList playlists;
    private final ObservableSet<Track> tracks;
    private final ObservableList<Track> trackList;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        playlists = new UniquePlaylistList();
        tracks = FXCollections.observableSet(new TreeSet<>());
        trackList = FXCollections.observableList(new ArrayList<>());
        // tracks = FXCollections.observableSet(new TreeSet<>());
    }

    public Library() {}

    /**
     * Creates a Library using the Playlists in the {@code toBeCopied}
     */
    public Library(ReadOnlyLibrary toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations


    @Override
    public ObservableList<Playlist> getPlaylistList() {
        return playlists.asUnmodifiableObservableList();
    }

    @Override
    public ObservableSet<Track> getTracks() {
        return FXCollections.unmodifiableObservableSet(tracks);
    }

    @Override
    public ObservableList<Track> getObservableTrackList() {
        return trackList;
    }

    /**
     * Replaces the contents of the playlist list with {@code playlists}.
     * @param playlists must not contain duplicate playlists.
     */
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists.setPlaylists(playlists);
    }

    /**
     * Replaces the contents of the track set with {@code tracks}. Set is used to ensure no duplicates.
     * @param tracks the new track ObservableSet. Cannot be null but can be empty.
     */
    public void setTracks(ObservableSet<Track> tracks) {
        requireNonNull(tracks);
        this.tracks.clear();
        this.tracks.addAll(tracks);
    }

    /**
     * Replaces the contents of the track list with {@code tracks}.
     * @param tracks the new track ObservableSet. Cannot be null but can be empty.
     */
    public void setTrackList(ObservableSet<Track> tracks) {
        requireNonNull(tracks);
        this.trackList.clear();
        this.trackList.addAll(tracks);
    }


    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyLibrary newData) {
        requireNonNull(newData);
        setPlaylists(newData.getPlaylistList());
        setTracks(newData.getTracks());
        setTrackList(newData.getTracks());
    }

    //// playlist-level operations

    /**
     * Returns true if a playlist with the same identity as {@code playlist} exists in the library.
     */
    public boolean hasPlaylist(Playlist playlist) {
        requireNonNull(playlist);
        return playlists.contains(playlist);
    }

    /**
     * Adds a playlist to the library.
     * The playlist must not already exist in the library.
     */
    public void addPlaylist(Playlist p) {
        playlists.add(p);
    }
    /**
     * Adds a track to the library.
     * The track must not already exist in the library.
     */
    public void addTrack(Track t) {
        tracks.add(t);
    }

    /**
     * Replaces the given playlist {@code target} in the list with {@code editedPlaylist}.
     * {@code target} must exist in the library.
     * The playlist identity of {@code editedPlaylist} must not be the same as another existing playlist in the library.
     */
    public void updatePlaylist(Playlist target, Playlist editedPlaylist) {
        requireNonNull(editedPlaylist);

        playlists.replacePlaylist(target, editedPlaylist);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the library.
     */
    public void removePlaylist(Playlist key) {
        playlists.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return playlists.asUnmodifiableObservableList().size() + " playlists";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Library // instanceof handles nulls
                && playlists.equals(((Library) other).playlists));
    }

    @Override
    public int hashCode() {
        return playlists.hashCode();
    }
}
