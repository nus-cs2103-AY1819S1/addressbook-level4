package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.jxmusic.commons.core.Messages;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.TrackNameContainsKeywordsPredicate;

/**
 * Search the desired Track from the library whose name contains any of the argument keywords.
 */
public class TrackSearchCommand extends Command {
    public static final String COMMAND_PHRASE = "track search";
    public static final String MESSAGE_USAGE = COMMAND_PHRASE + ": Searches for all tracks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_PHRASE + " Marbles";
    private final TrackNameContainsKeywordsPredicate predicate;
    public TrackSearchCommand(TrackNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTrackList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRACKS_LISTED_OVERVIEW, model.getFilteredTrackList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TrackSearchCommand // instanceof handles nulls
                && predicate.equals(((TrackSearchCommand) other).predicate)); // state check
    }
}