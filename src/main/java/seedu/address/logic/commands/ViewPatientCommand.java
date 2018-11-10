package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;

import seedu.address.calendar.GoogleCalendar;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * View a patient's information.
 */
public class ViewPatientCommand extends Command {

    public static final String COMMAND_WORD = "view-patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the patient identified by the name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "Viewing Patient: %1$s";
    public static final String MESSAGE_INVALID_PATIENT = "This patient does not exist in the HealthBook";
    public static final String MESSAGE_DUPLICATE_VIEW_PATIENT =
            "There is multiple patients with this name. Please enter doctor's number to identify the unique patient.";

    private final Name name;
    private final Phone phone;

    public ViewPatientCommand(Name name, Phone phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        Patient patientToView = null;
        for (Person person : personList) {
            if (person.getName().equals(name) && person instanceof Patient) {
                if (phone != null) {
                    if (person.getPhone().equals(phone)) {
                        patientToView = (Patient) person;
                    }
                } else {
                    if (patientToView != null && patientToView.getName().equals(name)) {
                        throw new CommandException(MESSAGE_DUPLICATE_VIEW_PATIENT);
                    } else {
                        patientToView = (Patient) person;
                    }
                }
            }
        }

        if (patientToView == null) {
            throw new CommandException(MESSAGE_INVALID_PATIENT);
        }

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(patientToView));
        return new CommandResult(String.format(MESSAGE_SUCCESS, patientToView.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewPatientCommand // instanceof handles nulls
                && name.equals(((ViewPatientCommand) other).name)); // state check
    }
}
