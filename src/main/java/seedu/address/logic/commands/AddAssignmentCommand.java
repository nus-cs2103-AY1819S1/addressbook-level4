package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Assignment;

/**
 * Adds a project to the address book.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "addassignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to OASIS. "
            + "Parameters: "
            + PREFIX_ASSIGNMENT_NAME + " ASSIGNMENT NAME "
            + PREFIX_AUTHOR + " AUTHOR "
            + PREFIX_ASSIGNMENT_DESCRIPTION + " DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ASSIGNMENT_NAME + " OASIS "
            + PREFIX_AUTHOR + " Amy Bee "
            + PREFIX_ASSIGNMENT_DESCRIPTION + " Management system for all. ";

    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the system.";

    private final Assignment toAdd;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Assignment assignment) {
        requireNonNull(assignment);
        toAdd = assignment;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
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
