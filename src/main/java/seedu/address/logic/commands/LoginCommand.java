package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

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
            + "Example: login r/doctor n/Adam Bell pass/doctor1";

    public static final String MESSAGE_SUCCESS = "Login successful.";
    public static final String MESSAGE_FAILURE = "Login failed. Please try again.";

    private final Person toAuthenticate;

    /**
     * Creates an LoginCommand to add the specified {@code Person}.
     * This {@code Person} could possibly be a doctor or receptionist.
     */
    public LoginCommand(Person person) {
        requireNonNull(person);
        toAuthenticate = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        requireNonNull(model);

        if (toAuthenticate instanceof Doctor) {
            model.updateFilteredDoctorList(doctor -> doctor.equals(toAuthenticate));
            if (!model.getFilteredDoctorList().isEmpty()) {
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
