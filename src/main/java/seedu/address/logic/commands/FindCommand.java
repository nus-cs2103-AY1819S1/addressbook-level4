package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREAMBLE_EXACT_MATCH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.wish.NameContainsKeywordsPredicate;
import seedu.address.model.wish.WishContainsKeywordsPredicate;

/**
 * Finds and lists all wishes in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all wishes whose names, tags and remark contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers."
            + "The -e flag can be used to enforce matching with all keywords for a particular attribute.\n"
            + "Parameters: [-e] [n/NAME_KEYWORD]... [t/TAG_KEYWORD]... [r/REMARK_KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " " + PREAMBLE_EXACT_MATCH + " "
            + PREFIX_NAME + "Apple" + " " + PREFIX_TAG + "family" + " " + PREFIX_TAG + "rare";

    private final WishContainsKeywordsPredicate predicate;

    public FindCommand(WishContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWishList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_WISHES_LISTED_OVERVIEW, model.getFilteredSortedWishList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
