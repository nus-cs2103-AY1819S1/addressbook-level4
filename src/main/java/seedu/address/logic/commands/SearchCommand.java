package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.SchoolContainsKeywordPredicate;

/**
 * Finds and lists all persons in address book whose data fields match any of the argument keyword.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command{
    public static final String COMMAND_WORD = "search";
    public static final String SEARCH_DISPLAYED = "Found!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are tagged with "
            + "any of the specified keywords (case insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " soccer";

    private final SchoolContainsKeywordPredicate predicate;

    public SearchCommand(SchoolContainsKeywordPredicate p){
        this.predicate = p;
    }

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return new CommandResult(SEARCH_DISPLAYED);
    }
}
