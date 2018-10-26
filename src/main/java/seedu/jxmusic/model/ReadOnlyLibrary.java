package seedu.jxmusic.model;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 * Unmodifiable view of an jxmusic book
 */
public interface ReadOnlyLibrary {

    /**
     * Returns an unmodifiable view of the playlists list.
     * This list will not contain any duplicate playlists.
     */
    ObservableList<Playlist> getPlaylistList();

    /**
     * Get the tracks in library
     * @return the set of tracks
     */
    ObservableSet<Track> getTracks();

    /**
     * Returns an unmodifiable view of the track list.
     * This list will not contain any duplicate tracks.
     */
    ObservableList<Track> getObservableTrackList();
}
