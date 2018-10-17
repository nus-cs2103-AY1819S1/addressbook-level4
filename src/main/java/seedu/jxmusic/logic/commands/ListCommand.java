package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.model.Model.PREDICATE_SHOW_ALL_PLAYLISTS;

import seedu.jxmusic.model.Model;

/**
 * Lists all persons in the jxmusic book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPlaylistList(PREDICATE_SHOW_ALL_PLAYLISTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
