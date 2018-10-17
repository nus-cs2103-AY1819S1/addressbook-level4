package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.staff.Staff;

/**
 * Adds a staff into the inventory management system.
 */
public class AddStaffCommand extends Command {

    public static final String COMMAND_WORD = "add_staff";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a staff to the inventory management system. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NAME + "NAME "
            + PREFIX_ROLE + "ROLE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "johnd "
            + PREFIX_PASSWORD + "johndoe1 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ROLE + "user";

    public static final String MESSAGE_SUCCESS = "New staff added: %1s";
    public static final String MESSAGE_DUPLICATE_USER = "This user already exists in the system.";

    private final Staff toAdd;

    /**
     * Creates an AddUserCommand to add the specified {@code Staff}
     */
    public AddStaffCommand(Staff staff) {
        requireNonNull(staff);
        toAdd = staff;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasStaff(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_USER);
        }

        model.addStaff(toAdd);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals (Object other) {
        return other == this
                || (other instanceof AddStaffCommand
                && toAdd.equals(((AddStaffCommand) other).toAdd));
    }
}
