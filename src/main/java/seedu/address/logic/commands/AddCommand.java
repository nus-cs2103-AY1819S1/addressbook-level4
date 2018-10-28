package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a volunteer to the application.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to the application. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_BIRTHDAY + "BIRTHDAY "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_GENDER + "m "
            + PREFIX_BIRTHDAY + "01-10-1985 "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "driver";

    public static final String MESSAGE_SUCCESS = "New volunteer added: %1$s";
    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer already exists in the application.";

    private final Volunteer toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Volunteer}
     */
    public AddCommand(Volunteer volunteer) {
        requireNonNull(volunteer);
        toAdd = volunteer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasVolunteer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_VOLUNTEER);
        }

        model.addVolunteer(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
