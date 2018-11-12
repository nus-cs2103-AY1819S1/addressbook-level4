package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.function.Predicate;

import seedu.superta.commons.core.Messages;
import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;

/**
 * Finds and lists all students in the SuperTA client whose information matched with the entered keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose information match any of "
            + "the entered keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_STUDENT_ID + "STUDENT-ID]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    private final Predicate predicate;

    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
