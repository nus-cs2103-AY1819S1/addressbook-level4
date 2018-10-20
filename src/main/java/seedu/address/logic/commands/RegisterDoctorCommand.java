package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.doctor.Doctor;

/**
 * Adds a doctor to the health book.
 */
public class RegisterDoctorCommand extends Command {

    public static final String COMMAND_WORD = "register-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Register a doctor to the HealthBook. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New doctor registered: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This doctor already exists in the HealthBook";

    private final Doctor doctorToRegister;

    /**
     * Creates an AddDoctorCommand to add the specified {@code Doctor}
     */
    public RegisterDoctorCommand(Doctor doctor) {
        requireNonNull(doctor);
        doctorToRegister = doctor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(doctorToRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(doctorToRegister);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, doctorToRegister));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterDoctorCommand // instanceof handles nulls
                && doctorToRegister.equals(((RegisterDoctorCommand) other).doctorToRegister));
    }
}
