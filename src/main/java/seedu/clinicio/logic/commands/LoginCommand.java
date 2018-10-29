package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;

import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.person.Person;

//@@author jjlee050

/**
 * Authenticate user and provide them access to ClinicIO based on the role.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Authenticate user to provide "
            + "user access to ClinicIO based on the roles.\n"
            + "Parameters: " + COMMAND_WORD
            + "[" + PREFIX_ROLE + "ROLE]"
            + "[" + PREFIX_NAME + "NAME]"
            + "[" + PREFIX_PASSWORD + "PASSWORD]\n"
            + "Example: login r/staff n/Adam Bell pass/doctor1";

    public static final String MESSAGE_FAILURE = "Invalid login credentials. Please try again.";
    public static final String MESSAGE_NO_DOCTOR_FOUND = "No staff found.";
    public static final String MESSAGE_NO_RECEPTIONIST_FOUND = "No receptionist found.";
    public static final String MESSAGE_SUCCESS = "Login successful.";

    private final Person toAuthenticate;

    /**
     * Creates an LoginCommand to add the specified {@code Person}.
     * This {@code Person} could possibly be a staff or receptionist.
     */
    public LoginCommand(Person person) {
        requireNonNull(person);
        toAuthenticate = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        requireNonNull(model);

        // TODO:Attempt to modularise method below
        if (toAuthenticate instanceof Staff) { 
            Staff authenticatedStaff = (Staff) toAuthenticate;
            if (!model.hasStaff(authenticatedStaff)) {
                throw new CommandException(MESSAGE_NO_DOCTOR_FOUND);
            }

            Staff retrievedStaff = model.getStaff(authenticatedStaff);

            boolean isCorrectPassword = Password.verifyPassword(
                    authenticatedStaff.getPassword().toString(),
                    retrievedStaff.getPassword().toString());

            if (isCorrectPassword) {
                return new CommandResult(MESSAGE_SUCCESS);
            }
        } 
        return new CommandResult(MESSAGE_FAILURE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toAuthenticate.equals(((LoginCommand) other).toAuthenticate)); // state check
    }

}
