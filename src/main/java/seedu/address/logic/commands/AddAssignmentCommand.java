package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;

/**
 * Adds an assignment to the address book.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "addAssignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "ASSIGNMENTNAME "
            + PREFIX_WEIGHT + "WEIGHT "
            + PREFIX_DATE + "DEADLINE "
            + PREFIX_MARK + "MAX_MARK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Assignment1 "
            + PREFIX_WEIGHT + "15 "
            + PREFIX_DATE + "14/10/2019 "
            + PREFIX_MARK + "100 ";

    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book";

    private final Assignment toAdd;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Assignment assignment) {
        requireNonNull(assignment);
        toAdd = assignment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasAssignment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAssignment(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAssignmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAssignmentCommand) other).toAdd));
    }
}
