package seedu.jxmusic.testutil;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;

/**
 * A utility class to help with building Library objects.
 * Example usage: <br>
 *     {@code Library ab = new LibraryBuilder().withPlaylist("John", "Doe").build();}
 */
public class LibraryBuilder {

    private Library Library;

    public LibraryBuilder() {
        Library = new Library();
    }

    public LibraryBuilder(Library Library) {
        this.Library = Library;
    }

    /**
     * Adds a new {@code Playlist} to the {@code Library} that we are building.
     */
    public LibraryBuilder withPlaylist(Playlist playlist) {
        Library.addPlaylist(playlist);
        return this;
    }

    public Library build() {
        return Library;
    }
}
