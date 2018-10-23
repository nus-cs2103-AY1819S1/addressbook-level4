package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.appointment.Appointment;

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
            + PREFIX_ID + "[Patient ID]"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "3 3 2003 "
            + PREFIX_TIME + "16 30"
            + PREFIX_ID + "289327";

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
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddApptCommand // instanceof handles nulls
                && toAdd.equals(((AddApptCommand) other).toAdd));
    }
}
