package seedu.jxmusic.model;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jxmusic.model.exceptions.DuplicatePlaylistException;
import seedu.jxmusic.model.exceptions.PlaylistNotFoundException;

/**
 * A list of playlists that enforces uniqueness between its elements and does not allow nulls.
 * A playlist is considered unique by comparing using {@code Playlist#isSamePlaylist(Playlist)}. As such, adding and updating of
 * playlists uses Playlist#isSamePlaylist(Playlist) for equality so as to ensure that the playlist being added or updated is
 * unique in terms of identity in the UniquePlaylistList. However, the removal of a playlist uses Playlist#equals(Object) so
 * as to ensure that the playlist with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Playlist#isSamePlaylist(Playlist)
 */
public class UniquePlaylistList implements Iterable<Playlist> {

    private final ObservableList<Playlist> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent playlist as the given argument.
     */
    public boolean contains(Playlist toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePlaylist);
    }

    /**
     * Adds a playlist to the list.
     * The playlist must not already exist in the list.
     */
    public void add(Playlist toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePlaylistException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the playlist {@code target} in the list with {@code editedPlaylist}.
     * {@code target} must exist in the list.
     * The playlist identity of {@code editedPlaylist} must not be the same as another existing playlist in the list.
     */
    public void replacePlaylist(Playlist target, Playlist editedPlaylist) {
        requireAllNonNull(target, editedPlaylist);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PlaylistNotFoundException();
        }

        if (!target.isSamePlaylist(editedPlaylist) && contains(editedPlaylist)) {
            throw new DuplicatePlaylistException();
        }

        internalList.set(index, editedPlaylist);
    }

    /**
     * Removes the equivalent playlist from the list.
     * The playlist must exist in the list.
     */
    public void remove(Playlist toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PlaylistNotFoundException();
        }
    }

    public void setPlaylists(UniquePlaylistList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code playlists}.
     * {@code playlists} must not contain duplicate playlists.
     */
    public void setPlaylists(List<Playlist> playlists) {
        requireAllNonNull(playlists);
        if (!playlistsAreUnique(playlists)) {
            throw new DuplicatePlaylistException();
        }

        internalList.setAll(playlists);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Playlist> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Playlist> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePlaylistList // instanceof handles nulls
                        && internalList.equals(((UniquePlaylistList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code playlists} contains only unique playlists.
     */
    private boolean playlistsAreUnique(List<Playlist> playlists) {
        for (int i = 0; i < playlists.size() - 1; i++) {
            for (int j = i + 1; j < playlists.size(); j++) {
                if (playlists.get(i).isSamePlaylist(playlists.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
