package seedu.saveit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.logic.commands.exceptions.CommandException;
import seedu.saveit.model.Model;
import seedu.saveit.model.issue.IssueHasTagsPredicate;

/**
 * Finds and lists all issues in saveIt that contains all the tags
 */
public class FindByTagCommand extends Command {
    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "ft";

    public static final String EMPTY_TAGS_ERROR_MESSAGE = "Please ensure that there is at least one t/ identifier "
            + "with an actual keyword (non-empty)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all issues that contain all the tags "
            + "represented by the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/KEYWORD [t/KEYWORD]*\n"
            + "Example: " + COMMAND_WORD + " t/java t/IndexOutOfBounds";

    private final IssueHasTagsPredicate predicate;

    public FindByTagCommand(IssueHasTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getCurrentDirectory().isRootLevel()) {
            model.filterIssues(predicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_ISSUES_LISTED_OVERVIEW,
                            model.getFilteredAndSortedIssueList().size()));
        } else {
            throw new CommandException(Messages.MESSAGE_WRONG_DIRECTORY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByTagCommand // instanceof handles nulls
                && predicate.equals(((FindByTagCommand) other).predicate)); // state check
    }
}
