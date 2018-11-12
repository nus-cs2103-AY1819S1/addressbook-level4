package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;

/**
 * Lists all appointments.
 */
public class ListAllApptCommand extends Command {

    public static final String COMMAND_WORD = "listallappt";

    public static final String MESSAGE_SUCCESS = "Listed all appointments!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.switchTab(1);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
