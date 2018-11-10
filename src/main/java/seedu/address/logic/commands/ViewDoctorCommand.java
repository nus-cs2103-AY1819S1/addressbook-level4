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
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * View a doctor's upcoming appointments.
 */
public class ViewDoctorCommand extends Command {

    public static final String COMMAND_WORD = "view-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the doctor identified by the name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "Viewing Doctor: %1$s";
    public static final String MESSAGE_INVALID_DOCTOR = "This doctor does not exist in the HealthBook";
    public static final String MESSAGE_DUPLICATE_VIEW_DOCTOR =
            "There is multiple doctors with this name. Please enter doctor's number to identify the unique doctor.";

    private final Name name;
    private final Phone phone;

    public ViewDoctorCommand(Name name, Phone phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        Doctor doctorToView = null;
        for (Person person : personList) {
            if (person.getName().equals(name) && person instanceof Doctor) {
                if (phone != null) {
                    if (person.getPhone().equals(phone)) {
                        doctorToView = (Doctor) person;
                    }
                } else {
                    if (doctorToView != null && doctorToView.getName().equals(name)) {
                        throw new CommandException(MESSAGE_DUPLICATE_VIEW_DOCTOR);
                    } else {
                        doctorToView = (Doctor) person;
                    }
                }
            }
        }

        if (doctorToView == null) {
            throw new CommandException(MESSAGE_INVALID_DOCTOR);
        }

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(doctorToView));
        return new CommandResult(String.format(MESSAGE_SUCCESS, doctorToView.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewDoctorCommand // instanceof handles nulls
                && name.equals(((ViewDoctorCommand) other).name)); // state check
    }
}
