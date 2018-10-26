package seedu.jxmusic.logic;

import javafx.collections.ObservableList;
import seedu.jxmusic.logic.commands.CommandResult;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns an unmodifiable view of the filtered list of playlists.
     */
    ObservableList<Playlist> getFilteredPlaylistList();

    /**
     * Returns an unmodifiable view of the filtered list of tracks.
     */
    ObservableList<Track> getFilteredTrackList();

    /**
     * Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object
     */
    ListElementPointer getHistorySnapshot();
}
