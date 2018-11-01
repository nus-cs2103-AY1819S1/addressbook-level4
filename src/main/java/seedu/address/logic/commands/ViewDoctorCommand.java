package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

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

/**
 * View a doctor's upcoming appointments.
 */
public class ViewDoctorCommand extends Command {

    public static final String COMMAND_WORD = "view-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the doctor identified by the name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "Viewing Doctor: %1$s";
    public static final String MESSAGE_INVALID_DOCTOR = "This doctor does not exist in the HealthBook";

    private final Name name;

    public ViewDoctorCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, GoogleCalendar googleCalendar)
            throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        Doctor doctor = (Doctor) personList.stream()
                .filter(person -> person.getName().equals(name) && person instanceof Doctor)
                .findFirst()
                .orElse(null);

        if (doctor == null) {
            throw new CommandException(MESSAGE_INVALID_DOCTOR);
        }

        EventsCenter.getInstance().post(new PersonPanelSelectionChangedEvent(doctor));
        return new CommandResult(String.format(MESSAGE_SUCCESS, doctor.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewDoctorCommand // instanceof handles nulls
                && name.equals(((ViewDoctorCommand) other).name)); // state check
    }
}
