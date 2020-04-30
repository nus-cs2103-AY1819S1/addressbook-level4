package seedu.restaurant.logic.commands.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.events.ui.reservation.DisplayReservationListRequestEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.model.Model;

/**
 * Lists all reservations in the reservation list to the user.
 */
public class ListReservationsCommand extends Command {

    public static final String COMMAND_WORD = "list-reservations";

    public static final String COMMAND_ALIAS = "lr";

    public static final String MESSAGE_SUCCESS = "Listed all reservations";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        EventsCenter.getInstance().post(new DisplayReservationListRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
