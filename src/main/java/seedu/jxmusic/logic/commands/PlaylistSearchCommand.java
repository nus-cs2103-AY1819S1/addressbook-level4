package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_QUERY;

import seedu.jxmusic.commons.core.Messages;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;

/**
 * Searches and lists all playlist in jxmusic book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class PlaylistSearchCommand extends Command {

    public static final String COMMAND_PHRASE = "playlist search";

    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Searches all playlists whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_PHRASE + " " + PREFIX_QUERY + "Fav\n";

    private final NameContainsKeywordsPredicate predicate;

    public PlaylistSearchCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPlaylistList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PLAYLISTS_LISTED_OVERVIEW, model.getFilteredPlaylistList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PlaylistSearchCommand // instanceof handles nulls
                && predicate.equals(((PlaylistSearchCommand) other).predicate)); // state check
    }
}
