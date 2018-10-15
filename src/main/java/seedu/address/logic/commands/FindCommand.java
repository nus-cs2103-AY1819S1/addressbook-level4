package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NONE;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
        + "[" + PREFIX_ALL + "KEYWORDS...(matches all keywords given)] "
        + "[" + PREFIX_SOME + "KEYWORDS...(matches some of the keywords given)] "
        + "[" + PREFIX_NONE + "KEYWORDS...(matches none of the keywords given)]\n"
        + "If no prefixes are used, it will default to match all keywords given.\n"
            + "Example: " + COMMAND_WORD + " "
        + PREFIX_ALL + "alice "
        + PREFIX_SOME + "bob "
        + PREFIX_NONE + "charlie\n"
        + "Prefixes not used: " + COMMAND_WORD + " david";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
