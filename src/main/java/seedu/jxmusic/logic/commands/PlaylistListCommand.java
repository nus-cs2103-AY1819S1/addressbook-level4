package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.model.Model.PREDICATE_SHOW_ALL_PLAYLISTS;

import seedu.jxmusic.model.Model;

/**
 * Lists all playlist inside library.
 */
public class PlaylistListCommand extends Command {
    public static final String COMMAND_PHRASE = "playlist list";

    public static final String MESSAGE_SUCCESS = "Listed all playlists";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPlaylistList(PREDICATE_SHOW_ALL_PLAYLISTS);
        return new CommandResult(MESSAGE_SUCCESS);

    }

}
