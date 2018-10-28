package seedu.address.logic.commands;

import java.util.function.Predicate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOME;

public abstract class FindCommand<E> extends Command {

    public static final String COMMAND_WORD = "find";

    //TODO update this
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

    protected final Predicate<E> predicate;

    FindCommand(Predicate<E> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
