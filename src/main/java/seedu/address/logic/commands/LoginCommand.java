package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.List;

import seedu.address.commons.util.HashUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;

//@@author jjlee050
/**
 * Authenticate user and provide them access to ClinicIO based on the role.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Authenticate user to provide"
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
     * Creates an LoginCommand to add the specified {@code Person}
     */
    public LoginCommand(Person person) {
        requireNonNull(person);
        toAuthenticate = person;
    }
    
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (toAuthenticate instanceof Doctor) {
            Doctor thisDoctor = (Doctor)toAuthenticate;
            List<Doctor> doctorsList = model.getFilteredDoctorList();
            for (Doctor d : doctorsList) {
            /*if(!d.isSameDoctor((Doctor)toAuthenticate)) {
                return new CommandResult(MESSAGE_FAILURE);        
            }*/
                if ((d.getName().equals(thisDoctor.getName()) && (d.getPassword().equals(thisDoctor.getPassword())))) {
                    return new CommandResult(MESSAGE_SUCCESS);
                }
            }
        }
        return new CommandResult(MESSAGE_FAILURE);
    }
}
