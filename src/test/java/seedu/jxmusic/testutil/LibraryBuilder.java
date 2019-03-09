package seedu.jxmusic.testutil;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;

/**
 * A utility class to help with building Library objects.
 * Example usage: <br>
 *     {@code Library ab = new LibraryBuilder().withPlaylist("John", "Doe").build();}
 */
public class LibraryBuilder {

    private Library library;

    public LibraryBuilder() {
        library = new Library();
    }

    public LibraryBuilder(Library library) {
        this.library = library;
    }

    /**
     * Adds a new {@code Playlist} to the {@code Library} that we are building.
     */
    public LibraryBuilder withPlaylist(Playlist playlist) {
        library.addPlaylist(playlist);
        return this;
    }

    public Library build() {
        return library;
    }
}
