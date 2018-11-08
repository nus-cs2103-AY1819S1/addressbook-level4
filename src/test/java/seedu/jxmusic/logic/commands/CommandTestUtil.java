package seedu.jxmusic.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.CommandHistory;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.TrackNameContainsKeywordsPredicate;
// import seedu.jxmusic.testutil.EditPlaylistDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_PLAYLIST_NAME_EMPTY = "Empty playlist";
    public static final String VALID_PLAYLIST_NAME_SFX = "Sound effects playlist";
    public static final String VALID_PLAYLIST_NAME_ANIME = "Anime music";
    public static final String VALID_PLAYLIST_NAME_INSTRUMENTAL = "Instrumental music";
    public static final String VALID_PLAYLIST_NAME_CHILL = "Chill music";
    public static final String VALID_PLAYLIST_NAME_ROCK = "Rock music";
    public static final String VALID_PLAYLIST_NAME_HIPHOP = "Hip Hop music";

    /** for sfx playlist */
    public static final String VALID_TRACK_NAME_SOS = "SOS Morse Code";
    /** for sfx playlist */
    public static final String VALID_TRACK_NAME_BELL = "Service Bell Help";
    /** for sfx playlist */
    public static final String VALID_TRACK_NAME_MARBLES = "Marbles";
    /** for anime playlist */
    public static final String VALID_TRACK_NAME_HAIKEI = "Haikei Goodbye Sayonara";
    /** for anime playlist */
    public static final String VALID_TRACK_NAME_IHOJIN = "Ihojin no Yaiba";

    public static final String PLAYLIST_NAME_ARG_SFX = " " + PREFIX_PLAYLIST + VALID_PLAYLIST_NAME_SFX;
    public static final String PLAYLIST_NAME_ARG_ANIME = " " + PREFIX_PLAYLIST + VALID_PLAYLIST_NAME_ANIME;

    public static final String TRACK_NAME_ARG_HAIKEI = " " + PREFIX_TRACK + VALID_TRACK_NAME_HAIKEI;
    public static final String TRACK_NAME_ARG_IHOJIN = " " + PREFIX_TRACK + VALID_TRACK_NAME_IHOJIN;
    public static final String TRACK_NAME_ARG_SOS = " " + PREFIX_TRACK + VALID_TRACK_NAME_SOS;
    public static final String TRACK_NAME_ARG_BELL = " " + PREFIX_TRACK + VALID_TRACK_NAME_BELL;
    public static final String TRACK_NAME_ARG_MARBLES = " " + PREFIX_TRACK + VALID_TRACK_NAME_MARBLES;

    // '&' not allowed in playlist name
    public static final String INVALID_PLAYLIST_NAME_ARG = " " + PREFIX_PLAYLIST + "Rock&Roll";
    // `'` not allowed in track name
    public static final String INVALID_TRACK_NAME_ARG = " " + PREFIX_TRACK + "Don't Stop Believin'";
    // name is valid but file does not exist
    public static final String INVALID_TRACK_FILE_NOT_EXIST_ARG = " " + PREFIX_TRACK + "no track file";
    // name is valid, file exists but file not supported
    public static final String INVALID_TRACK_FILE_NOT_SUPPORTED_ARG = " " + PREFIX_TRACK + "unsupported";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the jxmusic book and the filtered playlist list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Library expectedLibrary = new Library(actualModel.getLibrary());
        List<Playlist> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPlaylistList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedLibrary, actualModel.getLibrary());
            assertEquals(expectedFilteredList, actualModel.getFilteredPlaylistList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the playlist at the given {@code targetIndex} in the
     * {@code model}'s jxmusic book.
     */
    public static void showPlaylistAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPlaylistList().size());

        Playlist playlist = model.getFilteredPlaylistList().get(targetIndex.getZeroBased());
        final String[] splitName = playlist.getName().nameString.split("\\s+");
        model.updateFilteredPlaylistList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPlaylistList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the tracks at the given {@code targetIndex} in the
     * {@code model}'s jxmusic book.
     */
    public static void showTrackAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTrackList().size());

        Track track = model.getFilteredTrackList().get(targetIndex.getZeroBased());
        final String[] splitName = track.getFileNameWithoutExtension().split("\\s+");
        model.updateFilteredTrackList(new TrackNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTrackList().size());
    }

    /**
     * Deletes the first playlist in {@code model}'s filtered list from {@code model}'s jxmusic book.
     */
    public static void deleteFirstPlaylist(Model model) {
        Playlist firstPlaylist = model.getFilteredPlaylistList().get(0);
        model.deletePlaylist(firstPlaylist);
    }

}
