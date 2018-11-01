package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.jxmusic.commons.core.Messages;
import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;

/**
 * Deletes a playlist identified using it's displayed index from the jxmusic book.
 */
public class PlaylistDelCommand extends Command {

    public static final String COMMAND_PHRASE = "playlist del";

    public static final String MESSAGE_USAGE = COMMAND_PHRASE
            + ": Deletes the playlist identified by the index number used in the displayed playlist list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_PHRASE + "1";

    public static final String MESSAGE_DELETE_PLAYLIST_SUCCESS = "Deleted Playlist: %1$s";

    private final Index targetIndex;

    public PlaylistDelCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Playlist> lastShownList = model.getFilteredPlaylistList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
        }

        Playlist playlistToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePlaylist(playlistToDelete);
        //model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PLAYLIST_SUCCESS, playlistToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlaylistDelCommand // instanceof handles nulls
                && targetIndex.equals(((PlaylistDelCommand) other).targetIndex)); // state check
    }
}
