package seedu.jxmusic.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true **/
    Predicate<Playlist> PREDICATE_SHOW_ALL_PLAYLISTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyLibrary newData);

    /** Returns the Library */
    ReadOnlyLibrary getLibrary();

    /**
     * Returns true if a playlist with the same identity as {@code playlist} exists in the library.
     */
    boolean hasPlaylist(Playlist playlist);

    /**
     * Deletes the given playlist.
     * The playlist must exist in the library.
     */
    void deletePlaylist(Playlist target);

    /**
     * Adds the given playlist.
     * {@code playlist} must not already exist in the library.
     */
    void addPlaylist(Playlist playlist);

    /**
     * Replaces the given playlist {@code target} with {@code editedPlaylist}.
     * {@code target} must exist in the jxmusic book.
     * The playlist identity of {@code editedPlaylist} must not be the same as another existing playlist in the library.
     */
    void updatePlaylist(Playlist target, Playlist editedPlaylist);

    /** Returns an unmodifiable view of the filtered playlist list */
    ObservableList<Playlist> getFilteredPlaylistList();

    /** Returns an unmodifiable view of the filtered track list */
    ObservableList<Track> getFilteredTrackList();

    /**
     * Updates the filter of the filtered playlist list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPlaylistList(Predicate<Playlist> predicate);
}
