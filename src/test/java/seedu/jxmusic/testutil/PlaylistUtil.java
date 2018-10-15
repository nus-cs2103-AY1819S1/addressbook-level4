package seedu.jxmusic.testutil;

import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PLAYLIST;

import java.util.Set;

import seedu.jxmusic.logic.commands.PlaylistNewCommand;
import seedu.jxmusic.model.Playlist;

/**
 * A utility class for Playlist.
 */
public class PlaylistUtil {

    /**
     * Returns an add command string for adding the {@code playlist}.
     */
    public static String getPlaylistNewCommand(Playlist playlist) {
        return PlaylistNewCommand.COMMAND_PHRASE + " " + getPlaylistDetails(playlist);
    }

    /**
     * Returns the part of command string for the given {@code playlist}'s details.
     */
    public static String getPlaylistDetails(Playlist playlist) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_PLAYLIST + playlist.getName().nameString + " ");
        return sb.toString();
    }
}
