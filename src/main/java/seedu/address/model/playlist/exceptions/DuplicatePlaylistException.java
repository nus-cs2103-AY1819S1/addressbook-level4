package seedu.address.model.playlist.exceptions;

/**
 * Signals that the operation will result in duplicate Playlists (Playlists are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePlaylistException extends RuntimeException {
    public DuplicatePlaylistException() {
        super("Operation would result in duplicate playlists");
    }
}
