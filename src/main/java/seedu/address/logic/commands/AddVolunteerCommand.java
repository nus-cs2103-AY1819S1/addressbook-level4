package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a volunteer to the address book.
 */
public class AddVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to the address book. "
            + "Parameters: "
            + PREFIX_VOLUNTEER_NAME + "NAME "
            + PREFIX_VOLUNTEER_GENDER + "GENDER "
            + PREFIX_VOLUNTEER_BIRTHDAY + "BIRTHDAY "
            + PREFIX_VOLUNTEER_PHONE + "PHONE "
            + PREFIX_VOLUNTEER_EMAIL + "EMAIL "
            + PREFIX_VOLUNTEER_ADDRESS + "ADDRESS "
            + "[" + PREFIX_VOLUNTEER_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_VOLUNTEER_NAME + "John Doe "
            + PREFIX_VOLUNTEER_GENDER + "male "
            + PREFIX_VOLUNTEER_BIRTHDAY + "01-10-1985 "
            + PREFIX_VOLUNTEER_PHONE + "98765432 "
            + PREFIX_VOLUNTEER_EMAIL + "johnd@example.com "
            + PREFIX_VOLUNTEER_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_VOLUNTEER_TAG + "friends "
            + PREFIX_VOLUNTEER_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New volunteer added: %1$s";
    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "This volunteer already exists in the address book";

    private final Volunteer toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Volunteer}
     */
    public AddVolunteerCommand(Volunteer volunteer) {
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
                || (other instanceof AddVolunteerCommand // instanceof handles nulls
                && toAdd.equals(((AddVolunteerCommand) other).toAdd));
    }
}
