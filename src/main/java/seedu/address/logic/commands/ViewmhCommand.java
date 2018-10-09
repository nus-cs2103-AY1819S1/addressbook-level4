package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

//@@ omegafishy
/**
 * Views the existing medical records of a patient.
 */
public class ViewmhCommand extends Command {
    public static final String COMMAND_WORD = "viewmh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views a registered patient's medical history."
            + "Parameters: "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S9271847";

    public static final String MESSAGE_UNREGISTERED = "Person %1$s is not registered within the system.\n"
            + "Please register patient with \"addmh\" command";
    public static final String MESSAGE_NO_ENTRIES = "Patient %1$s has no existing records at present";
    public static final String MESSAGE_SUCCESS = "Displaying patient %s's medical records: %s\n";
    //todo add last updated timing

    private final Nric patientNric;

    public ViewmhCommand(Nric patientNric) {
        requireNonNull(patientNric);
        this.patientNric = patientNric;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> filteredByNric = model.getFilteredPersonList()
                .filtered(p -> patientNric.equals(p.getNric()));

        if (filteredByNric.size() < 1) {
            throw new CommandException(MESSAGE_UNREGISTERED);
        }

        Person patientToView = filteredByNric.get(0);
        MedicalHistory patientMedicalHistory = patientToView.getMedicalHistory();

        if (patientMedicalHistory.size() == 0) {
            return new CommandResult(String.format(MESSAGE_NO_ENTRIES, patientNric));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, patientToView.getNric(), patientMedicalHistory));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewmhCommand
                && patientNric.equals(((ViewmhCommand) other).patientNric));
    }
}
