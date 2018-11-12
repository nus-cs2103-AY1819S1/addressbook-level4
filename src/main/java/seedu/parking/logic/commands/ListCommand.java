package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.parking.model.Model.PREDICATE_SHOW_ALL_CARPARK;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.events.ui.ListCarparkRequestEvent;
import seedu.parking.logic.CommandHistory;
import seedu.parking.model.Model;

/**
 * Lists all car parks in the car park finder to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all %1$d car parks ";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCarparkList(PREDICATE_SHOW_ALL_CARPARK);
        int size = model.getCarparkFinder().getCarparkList().size();

        EventsCenter.getInstance().post(new ListCarparkRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, size));
    }
}
