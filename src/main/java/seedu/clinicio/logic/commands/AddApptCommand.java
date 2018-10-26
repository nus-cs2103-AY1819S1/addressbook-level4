package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.appointment.Appointment;

//@@author gingivitiss

/**
 * Adds appointment to schedule.
 */
public class AddApptCommand extends Command {

    public static final String COMMAND_WORD = "addappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new appointment to the scheduling calendar. "
            + "Parameters: "
            + PREFIX_DATE + "dd mm yyyy "
            + PREFIX_TIME + "hh mm "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_TYPE + "TYPE \n"
            + "Example: " + COMMAND_WORD + " "
            + "/d 3 3 2003 "
            + "/tm 16 30"
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TYPE + "followup ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment is already scheduled.";

    private final Appointment toAdd;

    public AddApptCommand(Appointment toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) throws CommandException {
        requireNonNull(model);
        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
        model.addAppointment(toAdd);
        model.commitClinicIo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddApptCommand // instanceof handles nulls
                && toAdd.equals(((AddApptCommand) other).toAdd));
    }
}
